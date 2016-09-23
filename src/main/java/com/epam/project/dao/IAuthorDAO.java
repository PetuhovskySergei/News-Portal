package com.epam.project.dao;

import java.sql.SQLException;
import java.util.List;

import com.epam.project.entity.Author;

public interface IAuthorDAO {
	
	public long insertAuthor(Author author) throws SQLException;
	
	public Author showAuthor(long authorId) throws SQLException;
	
	public void updateAuthor(Author author) throws SQLException;
	
	public void deleteAuthor(Long authorId) throws SQLException;
	
}

