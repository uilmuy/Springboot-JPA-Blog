```yml
server:
  port: 8080

spring:
  security:
    oauth2:
      client:
        registration:
          custom-client:
            client-id: {클라이언트 ID}
            client-secret: {클라이언트 Secret}
            scope: {API 스코프}
            redirect-uri: {리다이렉트 URI}
            authorization-grant-type: authorization_code
        provider:
          custom-provider:
            authorization-uri: {인증 URL}
            token-uri: {토큰 URL}

```
```yml
server:
  port: 8080

spring:
  security:
    oauth2:
      client:
        registration:
          custom-client:
            client-id: {클라이언트 ID}
            client-secret: {클라이언트 Secret}
            scope: "read,write"
            redirect-uri: http://localhost:8080/login/oauth2/code/custom-client
            authorization-grant-type: authorization_code
            client-name: custom-client
        provider:
          custom-provider:
            authorization-uri: http://test.com/login/oauth/authorize
            token-uri: http://localhost:8080/token
            user-info-uri: http://localhost:8080/user
            user-name-attribute: name

```

```java
@Configuration
@EnableWebSecurity
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    private OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    public OAuth2Config(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read", "write")
                .accessTokenValiditySeconds(60 * 60)
                .refreshTokenValiditySeconds(60 * 60 * 24);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                 .userDetailsService(customOAuth2UserService)
                 .successHandler(oAuth2LoginSuccessHandler)
                 .failureHandler(oAuth2LoginFailureHandler);
    }

    @Bean
    public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/error", "/webjars/**").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                    .and()
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler)
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
            .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
            .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        return http.build();
    }

}

```

```java
@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    public OAuth2LoginSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User = oauth2AuthenticationToken.getPrincipal();

        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");

        String jwtToken = jwtTokenProvider.createToken(email);

        Cookie jwtCookie = new Cookie("jwt", jwtToken);
        jwtCookie.setMaxAge(60 * 60); // 1 hour
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");

        response.addCookie(jwtCookie);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

```
위 코드는 OAuth2 인증에 성공하면 JWT 토큰을 발급하고 쿠키에 저장하는 OAuth2LoginSuccessHandler 클래스입니다.

JwtTokenProvider는 JWT 토큰을 생성하고 검증하는 클래스입니다. 이 클래스가 없다면 해당 클래스도 만들어야 합니다.

추가적으로, 이 클래스를 사용하려면 다음 의존성을 추가해야 합니다.
```xml
<dependency>
    <groupId>org.springframework.security.oauth.boot</groupId>
    <artifactId>spring-security-oauth2-autoconfigure</artifactId>
    <version>2.3.3.RELEASE</version>
</dependency>

```


```java
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2User);
        } catch (Exception e) {
            // 예외 처리
        }

        return null;
    }

    private OAuth2User processOAuth2User(OAuth2User oAuth2User) {
        String email = "";
        String name = "";

        if (oAuth2User.getAttribute("email") != null) {
            email = oAuth2User.getAttribute("email");
        }

        if (oAuth2User.getAttribute("name") != null) {
            name = oAuth2User.getAttribute("name");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "name");
    }
}

```

아래는 OAuth2LoginFailureHandler 클래스의 코드입니다. 이 클래스는 OAuth 2.0 로그인에 실패할 때 호출되는 핸들러입니다. 로그인에 실패한 경우 사용자를 로그인 페이지로 리디렉션합니다.
```java
@Component
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = "/";
        if (exception instanceof OAuth2AuthenticationException) {
            targetUrl = "/login?error";
        }
        super.setDefaultFailureUrl(targetUrl);
        super.onAuthenticationFailure(request, response, exception);
    }

}

```

이 핸들러는 `SimpleUrlAuthenticationFailureHandler`를 상속받아 구현되었습니다. `onAuthenticationFailure` 메소드는 로그인 실패시 호출되는 메소드이며, 로그인 실패 이유에 따라 리디렉션할 URL을 결정합니다. 만약 OAuth2 인증 예외인 경우, `/login?error`로 리디렉션합니다. 그렇지 않은 경우에는 홈 페이지로 리디렉션합니다.