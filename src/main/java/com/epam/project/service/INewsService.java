package com.epam.project.service;

import java.util.List;

import com.epam.project.entity.News;
import com.epam.project.entity.Tag;
import com.epam.project.service.exception.ServiceException;

public interface INewsService extends IGenericService<News> {
	
	public void addAuthor(Long authorId, Long newsId) throws ServiceException;
	
	public void addTag(Long newsId, Long tagId) throws ServiceException;
	
	public List<News> searchTag(Tag tag) throws ServiceException;
}

