package com.epam.project.dao;

import java.sql.SQLException;
import java.util.List;

import com.epam.project.entity.News;


public interface INewsDAO {
	
	public long insertNews(News news) throws SQLException;
	
	public News showNews(long newsId) throws SQLException;;
	
	public void updateNews(News news) throws SQLException;;
	
	public void deleteNews(Long newsId) throws SQLException;;
	
	public List<News> listNews() throws SQLException;
}

