<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>​
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>​
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>​​
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css"
	rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />

		<div id="content">
			<div id="user">
				<form:form 
					modelAttribute="userVo"
					id="join-form" 
					name="joinForm" 
					method="post" 
					action="${pageContext.request.contextPath }/user/join">
					<label class="block-label" for="name">이름</label> 
					<form:input path="name"/>
					<p style="text-align: left; padding-left: 0; color: #f00">
						<spring:hasBindErrors name="userVo">
							<c:if test="${errors.hasFieldErrors('name') }">
								<spring:message code="${errors.getFieldError('name').codes[0] }"></spring:message>
							</c:if>
						</spring:hasBindErrors>
					</p>
					
					<label class="block-label" for="email">이메일</label> 
						<form:input path="email"/> 
						<input id="btn-check-email" type="button" value="id 중복체크"> 
						<img id='img-check-email' src='${pageContext.request.contextPath }/assets/images/check.png' style='width:20px;display:none'/> 
					
					<p style="text-align: left; padding-left: 0; color: #f00">
						<form:errors path="email"/>
					</p>
						
					<label class="block-label">패스워드</label> 
					<form:password path="password"/>
					<p style="text-align: left; padding-left: 0; color: #f00">
						<spring:hasBindErrors name="userVo">
							<c:if test="${errors.hasFieldErrors('password') }">
								${errors.getFieldError('password').defaultMessage }
							</c:if>
						</spring:hasBindErrors>
					</p>

					<fieldset>
						<legend>성별</legend>
						<form:radiobutton path="gender" value="female" label="여"/> 
						<form:radiobutton path="gender" value="male" label="남"/>
					</fieldset>

					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>

					<input disabled id='btn-regist' type="submit" value="가입하기">

				</form:form>
			</div>
		</div>
		<p id='test'></p>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
	<script type="text/javascript">
		$(function() {
			$('#email').on("change paste input", () => {
				$('#btn-regist').attr('disabled', true)
				$("#btn-check-email").show()
				$("#img-check-email").hide()
			})
			
			$('#btn-check-email').click(() => {	
				let email = $('#email').val();
				if(email == ''){
					return;
				}
				
				$.ajax({
					url: `${pageContext.request.contextPath}/user/api/checkemail?email=` + email,
					type : 'GET',
					dataType : 'json',
					success: function(response){
						console.log(response)
						if(response.result == 'success'){
							alert('사용할 수 있는 이메일입니다.')
							$('#btn-regist').attr('disabled', false)
							$("#btn-check-email").hide()
							$("#img-check-email").show()
						}else{
							alert('사용할 수 없는 이메일입니다.')
							$('#btn-regist').attr('disabled', true)
							$("#email").val('');
							$("#email").focus();
						}
					},
					error: function(xhr,status,error){
						console.log(xhr)
						console.log(status)
						console.log(error)
					}
				})
				
			});
		})
	</script>
</body>
</html>