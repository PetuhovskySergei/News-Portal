package com.epam.project.dao;

import java.util.List;

public interface IGeneric<T> {
	
	public long insert(T entity);
	
	public T show(long id);
	
	public void update(T entity) ;
	
	public void delete(Long id) ;
	
	public List<T> readAll();
}
