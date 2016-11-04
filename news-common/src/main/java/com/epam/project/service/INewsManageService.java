package com.epam.project.service;

import java.util.List;

import com.epam.project.entity.NewsFull;
import com.epam.project.service.exception.ServiceException;
import com.epam.project.util.SearchCriteria;

public interface INewsManageService {
	/***
	 * Saves news with author and tags as one step.
	 * 
	 * @throws ServiceException
	 */
	public Long saveNews(NewsFull newsFull) throws ServiceException;

	/**
	 * Delete news with comments.
	 * 
	 * @param newsVO
	 * @throws ServiceException
	 */
	public void deleteNews(List<Long> newsListId) throws ServiceException;

	/**
	 * Update news with author and tags as one step.
	 * 
	 * @param newsVO
	 * @throws ServiceException
	 */
	public void updateNews(NewsFull newsFull) throws ServiceException;

	public NewsFull getSingleNewsFull(Long newsId) throws ServiceException;

	public List<NewsFull> searchNews(SearchCriteria searchCriteria,
			Integer numPage) throws ServiceException;
}
