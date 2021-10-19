<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="keyword" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${list }" var="vo" varStatus="status">
						<c:if test="${vo.isDeleted}">
							<tr>
								<td>${status.index }</td>
								
								<td style="text-align: left; padding-left:${12 * vo.depth}px">
									<c:if test="${vo.depth > 0 }">
										<img src="${pageContext.request.contextPath }/assets/images/reply.png"/>
									</c:if>
									<span>[삭제된 글입니다]</span>
								</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>						
						</c:if>
						
						<c:if test="${not vo.isDeleted }">
							<tr>
								<td>${status.index }</td>
								
								<td style="text-align: left; padding-left:${12 * vo.depth}px">
									<c:if test="${vo.depth > 0 }">
										<img src="${pageContext.request.contextPath }/assets/images/reply.png"/>
									</c:if>
									<a href="${pageContext.request.contextPath }/board?a=detail&no=${vo.no }&cur=${pager.curPage }">${vo.title }</a>
								</td>
								<td>${vo.userName }</td>
								<td>${vo.hit }</td>
								<td>${vo.regDate }</td>
								<td>
									<c:if test="${vo.userNo == user.no }">
										<a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no }" class="del">삭제</a>
									</c:if>
								</td>
							</tr>
						</c:if>
					</c:forEach>		
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${pager.prevPage == -1 }">
							<li>◀</li>
						</c:if>
						<c:if test="${pager.prevPage != -1 }">
							<li><a href="${pageContext.request.contextPath }/board?a=list&cur=${pager.prevPage}">◀</a></li>
						</c:if>
						
						<c:forEach begin="${pager.begin }" end="${pager.end }" step="1" varStatus="status">
							<c:choose>
								<c:when test="${status.index <= pager.totalPage }">
									<c:if test="${status.index == pager.curPage}">							
										<li class="selected">${status.index}</li>					
									</c:if>
									<c:if test="${status.index != pager.curPage}">
										<li><a href="${pageContext.request.contextPath }/board?a=list&cur=${status.index}">${status.index}</a></li>
									</c:if>								
								</c:when>
								
								<c:otherwise>
									<li>${status.index }</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						
						
						
						<c:if test="${pager.nextPage != -1 }">
							<li><a href="${pageContext.request.contextPath }/board?a=list&cur=${pager.nextPage }">▶</a></li>
						</c:if>
						<c:if test="${pager.nextPage == -1 }">
							<li>▶</li>
						</c:if>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test="${user != null}">
						<a href="${pageContext.request.contextPath }/board?a=writeform"
							id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>