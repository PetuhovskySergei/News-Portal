<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel='stylesheet' href='../resources/core/css/style.css'>
</head>
<body>
	<div id='wrapper'>
		<header id='header'> <span id='newsportaltext'> News
			portal</span>
		<div id='enruClient'>
			<a href='#'> EN </a> <a href='#'> RU </a>
		</div>
		</header>
		
		
		<div class="content">

		<div class="back">
			<a href="/news-client/">Back</a>
		</div>
		<div>
			<div class="news-title">
				<b><c:out value="${newsFull.news.mainTitle}" /></b>
			</div>
			<div class="news-author">
				<c:out value="(${newsFull.author.authorName})" />
			</div>
			<div class="news-date">
				<c:out value="${newsFull.news.publishDate}" />
			</div>
			<div class="news-full-text">
				<c:out value="${newsFull.news.text}" />
			</div>
		</div>
		<div class="comments_block">
			<c:forEach items="${newsFull.commentsList}" var="comment">
				<span><fmt:formatDate value="${comment.commentDate}" /></span>
				<div class="comment">
					<div class="comment-text">
						<c:out value="${comment.commentText}"></c:out>
					</div>
				</div>
			</c:forEach>
			<div class="addComm">
				<form
					action="/news-client/viewNews/${newsFull.news.newsId}/addComment"
					method="POST" onsubmit="return validate(this, 'commentForm')">
					<textarea class="commentTextarea" name="commentText"
						id="commentText" maxlength="100"></textarea>
					<input class="big_button" type="submit"> 
						<input
						type="hidden" name="newsId" value="${newsFull.news.newsId}">
				</form>
			</div>

		</div>


		<div class="prev-next">
			<c:if test="${not empty previousNewsId}">
				<a class="previous" href="/news-client/viewNews/${previousNewsId}">Prev</a>
			</c:if>

			<c:if test="${not empty nextNewsId}">
				<a class="next" href="/news-client/viewNews/${nextNewsId}">Next</a>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>