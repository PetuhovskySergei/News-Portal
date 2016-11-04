package com.epam.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.epam.project.dao.exception.DAOException;
import com.epam.project.dao.impl.NewsDAO;
import com.epam.project.entity.Author;
import com.epam.project.entity.Comment;
import com.epam.project.entity.News;
import com.epam.project.entity.Tag;
import com.epam.project.service.INewsService;
import com.epam.project.service.exception.ServiceException;
import com.epam.project.util.SearchCriteria;

@Controller
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
	public void addAuthor(Long newsId, Long authorId) throws ServiceException {

		if (newsId == null) {
			throw new ServiceException("NewsId is empty");
		}
		if (authorId == null) {
			throw new ServiceException("AuthorId is empty");
		}
		try {
			newsDAO.addAuthor(newsId, authorId);
		} catch (DAOException e) {
			throw new ServiceException("Can not add author for news.", e);
		}

	}

	@Override
	public void addTag(Long newsId, Long tagId) throws ServiceException {

		if (newsId == null) {
			throw new ServiceException("NewsId is empty");
		}

		if (tagId == null) {
			throw new ServiceException("TagId is empty");
		}
		try {
			newsDAO.addTag(newsId, tagId);
		} catch (DAOException e) {
			throw new ServiceException("Can not add tag for news.", e);
		}

	}

	@Override
	public List<News> searchByTag(Tag tag) throws ServiceException {

		List<News> newsList;
		if (tag == null) {
			throw new ServiceException("Tag is null.");
		}
		try {
			newsList = newsDAO.searchByTag(tag);
		} catch (DAOException e) {
			throw new ServiceException("Search news by tag is failed", e);
		}
		return newsList;
	}

	@Override
	public List<News> searchNews(SearchCriteria searchCriteria, Integer numPage)
			throws ServiceException {
		List<News> newsList = null;

		if (searchCriteria == null) {
			throw new ServiceException("Search criteria is empty");
		}
		try {
			newsList = newsDAO.searchNews(searchCriteria, numPage);
		} catch (DAOException e) {
			throw new ServiceException("Can not search news by criteria.", e);
		}
		return newsList;
	}

	@Override
	public void deleteAuthorNews(Long newsId) throws ServiceException {

		if (newsId == null) {
			throw new ServiceException("NewsId is empty");
		}
		try {
			newsDAO.deleteAuthorNews(newsId);
		} catch (DAOException e) {
			throw new ServiceException(
					"Can not delete relation between author and news.", e);
		}

	}

	@Override
	public void deleteTagNews(Long newsId) throws ServiceException {

		if (newsId == null) {
			throw new ServiceException("NewsId is empty");
		}
		try {
			newsDAO.deleteTagNews(newsId);
		} catch (DAOException e) {
			throw new ServiceException(
					"Can not delete relation between tag and news.", e);
		}

	}

	@Override
	public void updateAuthorNews(Long newsId, Long authorId)
			throws ServiceException {

		if (newsId == null) {
			throw new ServiceException("NewsId is empty");
		}
		if (authorId == null) {
			throw new ServiceException("AuthorId is empty");
		}
		try {
			newsDAO.updateAuthorNews(newsId, authorId);
		} catch (DAOException e) {
			throw new ServiceException("Can not update author news.", e);
		}
	}

	@Override
	public void updateTagsNews(Long newsId, Long tagId) throws ServiceException {

		if (newsId == null) {
			throw new ServiceException("NewsId is empty");
		}
		if (tagId == null) {
			throw new ServiceException("TagId is empty");
		}
		try {
			newsDAO.updateTagNews(newsId, tagId);
		} catch (DAOException e) {
			throw new ServiceException("Can not update tags news.", e);
		}
	}

	@Override
	public Long getNextNews(SearchCriteria searchCriteria, Long newsId)
			throws ServiceException {
		Long nextNewsId = null;

		if (searchCriteria == null) {
			throw new ServiceException("Search criteria is empty");
		}
		if (newsId == null) {
			throw new ServiceException("NewsId is empty");
		}

		try {
			nextNewsId = newsDAO.getNextNews(searchCriteria, newsId);
		} catch (DAOException e) {
			throw new ServiceException("Can not get next news");
		}
		return nextNewsId;
	}

	@Override
	public Long getPreviousNews(SearchCriteria searchCriteria, Long newsId)
			throws ServiceException {
		Long previousNewsId = null;

		if (searchCriteria == null) {
			throw new ServiceException("Search criteria is empty");
		}
		if (newsId == null) {
			throw new ServiceException("NewsId is empty");
		}
		try {
			previousNewsId = newsDAO.getPreviousNews(searchCriteria, newsId);
		} catch (DAOException e) {
			throw new ServiceException("Can not get previous news");
		}
		return previousNewsId;
	}

	@Override
	public List<Comment> getCommentsNews(Long newsId) throws ServiceException {
		List<Comment> commentsList = null;

		if (newsId == null) {
			throw new ServiceException("NewsId is empty");
		}
		try {
			commentsList = newsDAO.getCommentsNews(newsId);
		} catch (DAOException e) {
			throw new ServiceException("Can not get list of comments");
		}
		return commentsList;
	}

	@Override
	public List<Tag> getTagsNews(Long newsId) throws ServiceException {
		List<Tag> tagsList = null;

		if (newsId == null) {
			throw new ServiceException("NewsId is empty");
		}
		try {
			tagsList = newsDAO.getTagsNews(newsId);
		} catch (DAOException e) {
			throw new ServiceException("Can not get list of tags");
		}
		return tagsList;
	}

	@Override
	public Author getAuthorNews(Long newsId) throws ServiceException {
		Author author = null;

		if (newsId == null) {
			throw new ServiceException("NewsId is empty");
		}
		try {
			author = newsDAO.getAuthorNews(newsId);
		} catch (DAOException e) {
			throw new ServiceException("Can not get author news");
		}
		return author;
	}
}