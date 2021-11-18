<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook_spa.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript"
	src='${pageContext.request.contextPath }/assets/js/ejs/ejs.js'></script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" name='name' id="input-name" placeholder="이름">
					<input type="password" name='password' id="input-password"
						placeholder="비밀번호">
					<textarea id="tx-content" name='message' placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips error" style="display: none">비밀번호가 틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value=""
						class="text ui-widget-content ui-corner-all"> <input
						type="hidden" id="hidden-no" value=""> <input
						type="submit" tabindex="-1"
						style="position: absolute; top: -1000px">
				</form>
			</div>
			<div id="dialog-message" title="message-box" style="display: none">
				<p id='msg'></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
	<script type="text/javascript">
		var onFetch = false;
		
		window.addEventListener('scroll', function(e){
			var scrollDepth 
				= (($(window).scrollTop() + $(window).height())/$(document).height()) * 100
			
			console.log('onFetch=====================================', onFetch)
			
			if(scrollDepth > 98 && !onFetch){
				onFetch = true;
					fetch(5)
			}
		})	
		var listItemEJS = new EJS({
			url : '${pageContext.request.contextPath }/assets/js/ejs/listitem-template.ejs'
		});
		var listEJS = new EJS({
			url : '${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs'
		});
		
		var getFormDataObj = function(){
			var obj = {}
			$('#add-form')
				.serializeArray()
				.forEach((v) => {
					obj[v.name] = v.value
				})
			return obj;
		}
		var fetch = function(count){
			if(!count) count = 10;
			
			var lastNo = $('#list-guestbook li').last().data('no') || 0;	
			var requestUrl = '${pageContext.request.contextPath }/api/guestbook?count=' + count
							+ (lastNo? '&no=' + lastNo : '');
			$.ajax({
				url: requestUrl,
				type : 'get',
				dataType : 'json',
				success : function(response){
					console.log(response);
					
					var html = listEJS.render(response);
					$('#list-guestbook').append(html)
					setTimeout(() => onFetch = false ,1000)
				}
			})
		}		
		$(function() {
			fetch(5);
			var dialogDelete = $('#dialog-delete-form').dialog({
				autoOpen : false,
				modal : true,
				buttons : {
					'삭제' : function(){
						// ajax
						
						$.ajax({
							url: `${pageContext.request.contextPath}/api/guestbook/` + $('#hidden-no').val(),
							type : 'delete',
							dataType : 'json',
							data: {
								password : $('#password-delete').val()
							},
							success : function(response){
								if(response.result == 'fail') {
									alert('비밀번호가 틀렸습니다.')
									$('#password-delete').focus()
									return;
								}
								$('#list-guestbook li[data-no=' + response.data.no + ']').remove()
								dialogDelete.dialog('close')
							},
							error : function(){
								
							}
						})
						$('#password-delete').val('')
					},
					'취소' : function(){
						$(this).dialog('close')
					}
				},
				close : function(){
						$('#password-delete').val('')					
				}
			})
			
			//글 삭제 버튼
			$(document).on('click', '#list-guestbook li a',function(e){
				e.preventDefault();
				
				var no = $(this).data('no')
				console.log(no)
				$('#hidden-no').val(no)
				dialogDelete.dialog('open')
			})
			
			$('#add-form').submit(function(e) {
				e.preventDefault();

				if (!$('#input-name').val()) {
					messageBox('새글 작성', '이름은 반드시 입력해야합니다', () => $('#input-name').focus())
					return;
				}
				if(!$('#input-password').val()){
					messageBox('새글 작성', '패스워드는 반드시 입력해야합니다', () => $('#input-password').focus())
					return;					
				}
				if(!$('#tx-content').val()){
					messageBox('새글 작성', '글 내용은 반드시 입력해야합니다', () => $('#tx-content').focus())
					return;					
				}
				
				$.ajax({
					url : `${pageContext.request.contextPath }/api/guestbook`,
					type : 'post',
					contentType : 'application/json',
					data : JSON.stringify(getFormDataObj()),
					success : function(response){
						if(!response || response.result != 'success'){
							console.error(response.message)
							return;
						}
						alert('성공')
						console.dir(response)
						$('#list-guestbook').prepend(listItemEJS.render(response.data))
						document.getElementById('add-form').reset();
					},
					error : function(){
						alert('실패')
					}
				})
			})
		})
 		var messageBox = function(title, message, callback) {
			$('#dialog-message').attr('title', title)
			$('#dialog-message p').text(message)
			$('#dialog-message').dialog({
				modal : true,
				buttons : {
					"확인" : function() {
						$(this).dialog('close')
					}
				},
				close : callback
			});
		}
	</script>
</body>
</html>