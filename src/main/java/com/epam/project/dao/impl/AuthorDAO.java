package com.epam.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.epam.project.dao.IAuthorDAO;
import com.epam.project.entity.Author;

public class AuthorDAO implements IAuthorDAO{
	
	private DataSource dataSource;
	
	private final String SHOW_AUTHOR = "SELECT author_id, author_name FROM author "
										+ "WHERE author_id = ?";
	
	private final String INSERT_AUTHOR = "INSERT INTO author "
										+ "VALUES(default, ?)";
	
	private final String DELETE_AUTHOR = "DELETE FROM author "
										+ "WHERE author_id = ?";
	
	private final String UPDATE_AUTHOR = "UPDATE author "
										+ "SET author_name = ? "
										+ "WHERE author_id = ?";
	
	private final String AUTHOR_ID = "author_id";

	
	public Author showAuthor(long authorId) throws SQLException {
		 
		ResultSet rs = null;
		Author author = new Author();		
			try{
				Connection connect = dataSource.getConnection();	
				PreparedStatement  preparedStatement = connect.prepareStatement(SHOW_AUTHOR);
				
				preparedStatement.setLong(1, authorId);
				rs = preparedStatement.executeQuery();			
					if(rs.next()){
						author.setAuthorId(rs.getLong("AUTHOR_ID"));
						author.setAuthorName(rs.getString("AUTHOR_NAME"));
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return author;
	}
	
	
	public long insertAuthor(Author author) throws SQLException{	 
		try{
		Connection connect = dataSource.getConnection();
		PreparedStatement preparedStatement = connect.prepareStatement(INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS);
		Statement statement = connect.createStatement();
		
	          preparedStatement.setString(1, author.getAuthorName());
	          preparedStatement.executeUpdate();
	          
	          ResultSet rs = preparedStatement.getGeneratedKeys();
	          if (rs.next())
	        	  return rs.getLong(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	          return -1;
	}
	
	
	public void deleteAuthor(Long authorId) throws SQLException {

		try{
		Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(DELETE_AUTHOR);
				preparedStatement.setLong(1, authorId);
				preparedStatement.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
	public void updateAuthor(Author author) throws SQLException{	 
		try{
		Connection connect = dataSource.getConnection();	
		PreparedStatement  preparedStatement = connect.prepareStatement(UPDATE_AUTHOR); 
		
		 	  preparedStatement.setString(1, author.getAuthorName());
	          preparedStatement.setLong(2, author.getAuthorId());
	          preparedStatement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	private void close(ResultSet rs) throws SQLException{
		
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
