package com.epam.project.service;

import java.util.List;

import com.epam.project.service.exception.ServiceException;

public interface IGenericService <T> {

	public long insert(T entity) throws ServiceException;
	
	public T show(Long id) throws ServiceException;
	
	public void update(T entity) throws ServiceException;
	
	public void delete(Long id) throws ServiceException;
	
	public List<T> list() throws ServiceException;
}
