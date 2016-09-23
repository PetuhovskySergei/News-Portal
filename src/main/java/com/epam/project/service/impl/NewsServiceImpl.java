package com.epam.project.service.impl;

import java.util.List;

import com.epam.project.entity.News;
import com.epam.project.repository.NewsRepository;
import com.epam.project.service.NewsService;



public class NewsServiceImpl implements NewsService {
	
	private NewsRepository newsRepository;

	@Override
	public News insertNews(News news) {
		
		News savedNews = newsRepository.saveAndFlush(news);
		 
        return savedNews;
	}

	@Override
	public void deleteNews(long newsId) {
		
		newsRepository.delete(newsId);		
	}

	@Override
	public News showNews(long newsId) {
		
		return newsRepository.findOne(newsId);
	}

	@Override
	public void updateNews(News news) {
		
		  newsRepository.saveAndFlush(news);
		
	}

	@Override
	public List<News> listNews() {
		return newsRepository.findAll();
	}
}