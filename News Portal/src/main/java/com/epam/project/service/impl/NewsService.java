package com.epam.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.project.dao.exception.DAOException;
import com.epam.project.dao.impl.NewsDAO;
import com.epam.project.entity.News;
import com.epam.project.entity.Tag;
import com.epam.project.service.INewsService;
import com.epam.project.service.exception.ServiceException;

public class NewsService implements INewsService {

	@Autowired
	private NewsDAO newsDAO;

	@Override
	public long insert(News news) throws ServiceException {

		long id = 0;

		if (news == null) {
			throw new ServiceException("News is null");
		}
		try {
			id = newsDAO.insert(news);
		} catch (DAOException e) {
			throw new ServiceException("Insert was failed", e);
		}
		return id;
	}

	@Override
	public News show(Long id) throws ServiceException {

		News news = null;
		try {
			news = newsDAO.show(id);
		} catch (DAOException e) {
			throw new ServiceException("Show was failed", e);
		}
		return news;
	}

	@Override
	public void update(News news) throws ServiceException {

		if (news == null) {
			throw new ServiceException("News is null");
		}

		try {
			newsDAO.update(news);
		} catch (DAOException e) {
			throw new ServiceException("Update was failed", e);
		}
	}

	@Override
	public void delete(Long id) throws ServiceException {

		try {
			newsDAO.delete(id);
		} catch (DAOException e) {
			throw new ServiceException("Delete was failed", e);
		}
	}

	@Override
	public List<News> list() throws ServiceException {

		List<News> list;
		try {
			list = newsDAO.list();
		} catch (DAOException e) {
			throw new ServiceException("List was failed.", e);
		}
		return list;
	}

	@Override
	public void addAuthor(Long authorId, Long newsId) throws ServiceException {
		try {
			newsDAO.addAuthor(newsId, authorId);
		} catch (DAOException e) {
			throw new ServiceException("Add author for news is failed", e);
		}
	}

	@Override
	public void addTag(Long newsId, Long tagId) throws ServiceException {
		try {
			newsDAO.addTag(newsId, tagId);
		} catch (DAOException e) {
			throw new ServiceException("Add tag for news is failed", e);
		}
	}

	@Override
	public List<News> searchTag(Tag tag) throws ServiceException {

		List<News> newsList;
		if (tag == null) {
			throw new ServiceException("Tag is null.");
		}
		try {
			newsList = newsDAO.searchTag(tag);
		} catch (DAOException e) {
			throw new ServiceException("Search news by tag is failed", e);
		}
		return newsList;
	}
}