<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NewsListClient</title>
<link rel='stylesheet' href='resources/core/css/style.css'>
<script src=<c:url value="/resources/core/js/dropdown.js" />></script>
</head>
<body>
	<div id='wrapper'>
		<header id='header'> <span id='newsportaltext'> News
			portal</span>
		<div id='enruClient'>
			<a href='#'> EN </a> <a href='#'> RU </a>
		</div>
		</header>
		
	<div class = "content">	
	<sf:form action="/news-client" modelAttribute="criteria" method="GET"
			class="filterForm">
     <div class="user_filter_block">
          <div class="selectAuthor">
             <p><sf:select path="authorId" class="dropdown">
						<sf:option value="0"  selected="true">
							Select Authors
						</sf:option>
						<c:forEach var="author" items="${authorsList}">
							<sf:option value="${author.authorId}">
								<c:out value="${author.authorName}" />
							</sf:option>
						</c:forEach>
					</sf:select>
            </p>
				
       		</div>       
    
    
     
      <div class="multiselectTags">
              <p><sf:select path="tagsIdList" class="dropdown">
						<sf:option value="0"  selected="true">
							Select Tags
						</sf:option>
						<c:forEach var="tag" items="${tagsList}">
							<sf:option value="${tag.tagId}">
								<c:out value="${tag.tagName}" />
							</sf:option>
						</c:forEach>
					</sf:select>
            </p>
          </div>
 		</div>      
 
     <input type="submit" name="filter" value="Filter" style="margin:15px"/>
     </sf:form>  
     
     <form action="/news-client/newslist" class="resetForm">
			<input class="btn" type="submit" name="reset" value="Reset">
		</form>
     
     
       <div class="newslist-container">
			<c:forEach var="newsFull" items="${newsList}">
				<div class="news-info">
					<div class="news-title">
						<c:out value="${newsFull.news.mainTitle}" />
					</div>
					<div class="news-author">
						<c:out value="(${newsFull.author.authorName})" />
					</div>
					<div class="news-date">
						
						<c:out value="${newsFull.news.publishDate}" />
					</div>
					<div class="news-short-text">
						<c:out value="${newsFull.news.shortTitle}" />
					</div>

					<div class="view-news">
						<a href="/news-client/viewNews/${newsFull.news.newsId}">View</a>
					</div>

					<div class="news-comments">
						<c:out value="(${newsFull.countComment})" />
					</div> 

					<div class="news-tags">
						<c:forEach var="tag" items="${newsFull.tagsList}">
							<span><c:out value="${tag.tagName}" /></span>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="pagination">
			<c:forEach begin="1" end="${pagesCount}" var="numPage">
				<a href="/news-client?numPage=${numPage}"><c:out
						value="${numPage}"></c:out></a>
			</c:forEach>
		
		</div> 
       			
		</div>
		
		</div>
		 
      
     
     
     
    
  
	

	 
</body>
</html>