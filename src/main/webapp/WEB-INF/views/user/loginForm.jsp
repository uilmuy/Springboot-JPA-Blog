<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %> --%>
<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">User name:</label> 
			<input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
		</div>
		<div class="form-group">
			<label for="password">password:</label> 
			<input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		
		<button type="submit" id="btn-login" class="btn btn-primary">로그인</button>
	</form>
	

</div>

<!-- <script src="/js/user.js" type="text/javascript"> 자바 스크립트를 사용하여 로그인을 사용하지 않기 때문에 버튼도 폼 안으로 넣었음

</script>-->
<%@ include file="../layout/footer.jsp"%>