<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.epam.project.dao.impl.AuthorDAO"%>
<%@page import="com.epam.project.dao.impl.TagDAO"%>
<%@page import="com.epam.project.entity.Author"%>
<%@page import="com.epam.project.entity.Tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NewsListClient</title>
<link rel='stylesheet' href='resources/css/style.css'>
</head>
<body>
	<div id = 'wrapper'>
		<header id = 'header'>
				<span id = 'newsportaltext'> News portal</span>
				<div id = 'enruClient'>
					<a href = '#'> EN </a>
					<a href = '#'> RU </a>
				</div>
		</header>
	</div>
	
	<form action="SaveSortedSelection">
	<SELECT NAME="listAuthor">
<%
	AuthorDAO authorDao = new AuthorDAO();
	List<Author> listAuthor = authorDao.list();
    	boolean selectedAuthor = false;
            for (int j = 0; j < listAuthor.size(); j++){
%>
        <OPTION VALUE = "<%= listAuthor.get(j).getAuthorId() %>"
            <%= selectedAuthor ? "SELECTED" : "" %>> <%= listAuthor.get(j).getAuthorName()%></OPTION>
<%
    }
%>
	 </SELECT>
	 
	 <SELECT NAME ="ListTag">
<%
	TagDAO tagDao = new TagDAO();
	List<Tag> listTag = tagDao.list();
    	boolean selectedTag = false;
            for (int j = 0; j < listTag.size(); j++){
%>
        <OPTION VALUE = "<%= listTag.get(j).getTagId() %>"
            <%= selectedTag ? "SELECTED" : "" %>> <%= listTag.get(j).getTagName()%></OPTION>
<%
    }
%>
	 </SELECT>
	
	<input type="submit">
</form>
</body>
</html>