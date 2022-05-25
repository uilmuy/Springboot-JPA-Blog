<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	<c:if test="${board.user.id == principal.user.id}">
		<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
	<br />
	<br />
	<div>
		<i>Number : <span id="id">${board.id} </span></i> <i>작성자 : <span>${board.user.username} </span></i>
	</div>
	<br />
	<div class="form-group">
		<h3>${board.title}</h3>
	</div>
	<hr />
	<div class="form-group">
		<div>${board.content}</div>
	</div>
	<hr />
</div>

<script src="/js/board.js" type="text/javascript"></script>
<%@ include file="../layout/footer.jsp"%>


