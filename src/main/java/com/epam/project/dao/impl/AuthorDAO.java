package com.epam.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.epam.project.dao.IAuthorDAO;
import com.epam.project.dao.exception.DAOException;
import com.epam.project.entity.Author;

public class AuthorDAO implements IAuthorDAO{
	
	private DataSource dataSource;
	
	private final static String LIST_AUTHOR = "SELECT author_id, author_name"
										+ "FROM comment";
	
	private final static String SHOW_AUTHOR = "SELECT author_id, author_name FROM author "
										+ "WHERE author_id = ?";
	
	private final static String INSERT_AUTHOR = "INSERT INTO author "
										+ "VALUES(default, ?)";
	
	private final static String DELETE_AUTHOR = "DELETE FROM author "
										+ "WHERE author_id = ?";
	
	private final static String UPDATE_AUTHOR = "UPDATE author "
										+ "SET author_name = ? "
										+ "WHERE author_id = ?";
	
	private final static String AUTHOR_ID = "author_id";

	
	public Author show(long authorId) throws DAOException {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Author author = null;		
			try{
				connect = dataSource.getConnection();	
				preparedStatement = connect.prepareStatement(SHOW_AUTHOR);			
						preparedStatement.setLong(1, authorId);
						rs = preparedStatement.executeQuery();			
					if(rs.next()){ 
						author = new Author();
						author.setAuthorId(rs.getLong("AUTHOR_ID"));
						author.setAuthorName(rs.getString("AUTHOR_NAME"));
					}
			} catch (SQLException e) {
				throw new DAOException(e); 
			} finally {
				closeResultSet(rs);
				closeConnection(connect);
				closeStatement(preparedStatement);
			}
			return author;
	}
	
	
	public long insert(Author author) throws DAOException{	 
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		long authorId = 0;
			try{
				connect = dataSource.getConnection();
				preparedStatement = connect.prepareStatement(INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS);
					  preparedStatement.setString(1, author.getAuthorName());
			          preparedStatement.executeUpdate();
			          
			          rs = preparedStatement.getGeneratedKeys();
			          if (rs.next())
			        	  authorId = rs.getLong(1);
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResultSet(rs);
			closeConnection(connect);
			closeStatement(preparedStatement);
		}
	          return authorId;
	}
	
	
	public List<Author> list() throws DAOException {
			 Connection connect = null;
	   		 ResultSet rs = null;
	   		 Statement statement = null;
			 Author author = null;
			 List<Author> result = new ArrayList<Author>();
				 try{
					 connect = dataSource.getConnection();	
					 statement = connect.createStatement();
					 rs = statement.executeQuery(LIST_AUTHOR);
					 	while(rs.next()){
					 		author = new Author();
					 		author.setAuthorId(rs.getLong("AUTHOR_ID"));
					 		author.setAuthorName(rs.getString("AUTHOR_NAME"));
					 			result.add(author);
					 					}
		}catch(SQLException e){
			throw new DAOException(e);
		} finally {
			closeResultSet(rs);
			closeConnection(connect);
			closeStatement(statement);
		}
		return result;
	}


	public void delete(Long authorId) throws DAOException {
			Connection connect = null;
			PreparedStatement preparedStatement = null;
				try{
					connect = dataSource.getConnection();
							preparedStatement = connect.prepareStatement(DELETE_AUTHOR);
							preparedStatement.setLong(1, authorId);
							preparedStatement.executeUpdate();
		}catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeConnection(connect);
			closeStatement(preparedStatement);
		}
	}
	
	
	public void update(Author author) throws DAOException{		
		Connection connect = null;
		PreparedStatement preparedStatement = null;
			try{
				connect = dataSource.getConnection();	
				preparedStatement = connect.prepareStatement(UPDATE_AUTHOR); 
				
				 	  preparedStatement.setString(1, author.getAuthorName());
			          preparedStatement.setLong(2, author.getAuthorId());
			          preparedStatement.executeUpdate();
		}catch(SQLException e){
			throw new DAOException(e);
		} finally {
			closeConnection(connect);
			closeStatement(preparedStatement);
		}
	}
	
	
	private void closeResultSet(ResultSet rs) throws DAOException{
		
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DAOException("Could not close ResultSet",e);
			}
		}
	}

	
	private void closeConnection(Connection connect) throws DAOException{
		if (connect != null) {
			try {
				connect.close();
			}
			catch (SQLException e) {
				throw new DAOException("Could not close Connection", e);
			}
		}
	}
	

	public void closeStatement(Statement statement) throws DAOException {
		if (statement != null) {
			try {
				statement.close();
			}
			catch (SQLException e) {
				throw new DAOException("Could not close Statement", e);
			}
			
		}
	}
}

