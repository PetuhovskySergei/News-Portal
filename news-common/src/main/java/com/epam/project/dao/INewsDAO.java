package com.epam.project.dao;

import java.util.List;

import com.epam.project.dao.exception.DAOException;
import com.epam.project.entity.Author;
import com.epam.project.entity.Comment;
import com.epam.project.entity.News;
import com.epam.project.entity.Tag;
import com.epam.project.util.SearchCriteria;

public interface INewsDAO extends IGenericDAO<News> {
	/**
	 * Add author for news.
	 * 
	 * @param authorId
	 *            the author id
	 * @param newsId
	 *            the news id
	 * @throws DAOException
	 */
	public void addAuthor(Long authorId, Long newsId) throws DAOException;

	/**
	 * Add tag for news.
	 * 
	 * @param newsId
	 *            the news id
	 * @param tagId
	 *            the tag id
	 * @throws DAOException
	 */
	public void addTag(Long newsId, Long tagId) throws DAOException;

	/**
	 * Destroys relation between author and news.
	 * 
	 * @param newsId
	 *            news ID
	 * @throws DAOException
	 */
	public void deleteAuthorNews(Long newsId) throws DAOException;

	/**
	 * Destroys relation between tag and news.
	 * 
	 * @param newsId
	 * @throws DAOException
	 */
	public void deleteTagNews(Long newsId) throws DAOException;

	/**
	 * Finds news persistent object by one of the criteria.
	 * 
	 * @param searchCriteria
	 *            criteria for searching
	 * @param numPage
	 *            number of the page
	 * @return list of found news
	 * @throws DAOException
	 */
	public List<News> searchNews(SearchCriteria searchCriteria, Integer numPage)
			throws DAOException;

	/**
	 * Finds news persistent object by author.
	 * 
	 * @param author
	 *            the author
	 * @return list of found news
	 * @throws DAOException
	 */
	public List<News> searchByAuthor(Author author) throws DAOException;

	/**
	 * Finds news persistent object by tag.
	 * 
	 * @param tag
	 *            the tag
	 * @return list of found news
	 * @throws DAOException
	 */
	public List<News> searchByTag(Tag tag) throws DAOException;

	/**
	 * Update author of the news.
	 * 
	 * @param newsId
	 *            news ID
	 * @param authorId
	 *            author ID
	 * @throws DAOException
	 */
	public void updateAuthorNews(Long newsId, Long authorId)
			throws DAOException;

	/**
	 * Update tag of the news.
	 * 
	 * @param newsId
	 *            news
	 * @param tagId
	 *            tag ID
	 * @throws DAOException
	 */
	public void updateTagNews(Long newsId, Long tagId) throws DAOException;

	/**
	 * Gets news ID of the next news.
	 * 
	 * @param searchCriteria
	 *            search criteria.
	 * @param newsId
	 *            news ID
	 * @return news ID of the next news.
	 * @throws DAOException
	 */
	public Long getNextNews(SearchCriteria searchCriteria, Long newsId)
			throws DAOException;

	/**
	 * Gets news ID of the previous news.
	 * 
	 * @param searchCriteria
	 *            search criteria.
	 * @param newsId
	 *            news ID
	 * @return news ID of the previous news
	 * @throws DAOException
	 */
	public Long getPreviousNews(SearchCriteria searchCriteria, Long newsId)
			throws DAOException;

	/**
	 * Gets comments of the specific news.
	 * 
	 * @param newsId
	 *            news ID.
	 * @return comments list.
	 * @throws DAOException
	 */
	public List<Comment> getCommentsNews(Long newsId) throws DAOException;

	/**
	 * Gets list of tags of the specific news.
	 * 
	 * @param newsId
	 *            news ID.
	 * @return list of tags of the specific news.
	 * @throws DAOException
	 */
	public List<Tag> getTagsNews(Long newsId) throws DAOException;

	/**
	 * Gets author news.
	 * 
	 * @param newsId
	 *            news ID.
	 * @return author news.
	 * @throws DAOException
	 */
	public Author getAuthorNews(Long newsId) throws DAOException;
}
