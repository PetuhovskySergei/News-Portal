package com.epam.project.service;

import java.util.List;

import com.epam.project.entity.Author;
import com.epam.project.entity.Comment;
import com.epam.project.entity.News;
import com.epam.project.entity.Tag;
import com.epam.project.service.exception.ServiceException;
import com.epam.project.util.SearchCriteria;

public interface INewsService extends IGenericService<News> {

	public void addAuthor(Long authorId, Long newsId) throws ServiceException;

	public void addTag(Long newsId, Long tagId) throws ServiceException;

	public List<News> searchByTag(Tag tag) throws ServiceException;

	public List<News> searchNews(SearchCriteria searchCriteria, Integer numPage)
			throws ServiceException;

	public void deleteAuthorNews(Long newsId) throws ServiceException;

	public void deleteTagNews(Long newsId) throws ServiceException;

	public void updateAuthorNews(Long newsId, Long authorId)
			throws ServiceException;

	public void updateTagsNews(Long newsId, Long tagId) throws ServiceException;

	public Long getNextNews(SearchCriteria searchCriteria, Long newsId)
			throws ServiceException;

	public Long getPreviousNews(SearchCriteria searchCriteria, Long newsId)
			throws ServiceException;

	public List<Comment> getCommentsNews(Long newsId) throws ServiceException;

	public List<Tag> getTagsNews(Long newsId) throws ServiceException;

	public Author getAuthorNews(Long newsId) throws ServiceException;
}
