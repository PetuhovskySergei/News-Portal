package com.epam.project.dao;

import java.util.List;

import com.epam.project.dao.exception.DAOException;

public interface IGenericDAO<T> {

	public long insert(T entity) throws DAOException;

	public T show(long id) throws DAOException;

	public void update(T entity) throws DAOException;

	public void delete(Long id) throws DAOException;

	public List<T> list() throws DAOException;
}
