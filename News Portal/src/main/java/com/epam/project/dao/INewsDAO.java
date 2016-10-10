package com.epam.project.dao;

import java.util.List;

import com.epam.project.dao.exception.DAOException;
import com.epam.project.entity.News;
import com.epam.project.entity.Tag;

public interface INewsDAO extends IGenericDAO<News> {

	public void addAuthor(Long authorId, Long newsId) throws DAOException;

	public void addTag(Long newsId, Long tagId) throws DAOException;

	public List<News> searchTag(Tag tag) throws DAOException;
}
