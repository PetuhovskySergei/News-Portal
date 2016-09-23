package com.epam.project.service;

import java.util.List;

import com.epam.project.entity.News;


public interface NewsService {
	List<News> listNews();
	News insertNews(News news);
	void deleteNews(long newsId);
	News showNews(long newsId);
	void updateNews(News news);
}
