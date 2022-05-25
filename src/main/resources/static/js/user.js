let index={
	init: function(){
		$("#btn-save").on("click",()=>{ //function()){}을 사용 하지 않고 ()=>을 사용하는 이유는 this를 바인딩 하기 위해서>
			this.save();
		});
		/*$("#btn-login").on("click",()=>{ //function()){}을 사용 하지 않고 ()=>을 사용하는 이유는 this를 바인딩 하기 위해서>
			this.login();
		});*/
	},
	save:function(){
		//alert('잘 나오나.');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		//console.log(data);
		//ajax호출시 default 비동기 호출
		//회원가입 요청
		$.ajax({                                 
			type:"POST",
			url:"/auth/joinProc",
			data:JSON.stringify(data), //http body데이터
			contentType:"application/json;charset=utf-8", //보낼 바디 데이터가 어떤 데이터 인지
			dataType : "json" // 서버로 부터의 응답이 왔을때 생긴것이 json타입이면 자바스크립트 오브젝트 형태로 변환 하여 준다.
		}).done(function(resp){                      //성공         
			alert("회원가입이 완료되었습니다.");
			//console.log(resp);
			location.href="/";
		}).fail(function(error){                      //실패
			alert(JSON.stringify(error));
		}); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 inset를 할거임.
	},
	/*login:function(){
		//alert('잘 나오나.');
		let data = {
			username: $("#username").val(),
			password: $("#password").val()
		};
		
		$.ajax({                                 
			type:"POST",
			url:"/api/login",
			data:JSON.stringify(data), //http body데이터
			contentType:"application/json;charset=utf-8", //보낼 바디 데이터가 어떤 데이터 인지
			dataType : "json" // 서버로 부터의 응답이 왔을때 생긴것이 json타입이면 자바스크립트 오브젝트 형태로 변환 하여 준다.
		}).done(function(resp){  
			//console.log(resp);                    //성공         
			alert("로그인이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){                      //실패
			alert(JSON.stringify(error));
		}); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 inset를 할거임.
	}*/
	
}
index.init();