package com.epam.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.epam.project.dao.ITagDAO;
import com.epam.project.dao.exception.DAOException;
import com.epam.project.entity.Tag;

public class TagDAO implements ITagDAO{
	
private DataSource dataSource;
	
	private final static String LIST_TAG = "SELECT tag_id, tag_name "
										+ "FROM tag";
	
	private final static String SHOW_TAG = "SELECT tag_id, tag_name "
										+ "FROM tag"
										+ "WHERE tag_id = ?";
	
	private final static String INSERT_TAG = "INSERT INTO tag"
										+ "VALUES(default, ?)";
	
	private final static String DELETE_TAG = "DELETE FROM tag"
										+ "WHERE tag_id = ?";
	
	private final static String UPDATE_TAG = "UPDATE tag"
										+ "SET tag_name = ?"
										+ "WHERE tag_id = ?";
	
	public List<Tag> list() throws DAOException {
		 Connection connect = null;
 		 ResultSet rs = null;
 		 Statement statement = null;
		 Tag tag = null; 
		 List<Tag> result = new ArrayList<Tag>();
		 try{
			 connect = dataSource.getConnection();	
			 statement = connect.createStatement();
			 rs = statement.executeQuery(LIST_TAG);
		 	while(rs.next()){
		 		tag = new Tag();
		 		tag.setTagId(rs.getLong("TAG_ID"));
		 		tag.setTagName(rs.getString("TAG_NAME"));
		 			result.add(tag);
		 					}
		} catch(SQLException e){
			throw new DAOException(e);
		} finally {
			closeResultSet(rs);
			closeConnection(connect);
			closeStatement(statement);
		}
		return result;
	}
	
	
	public Tag show(long tagId) throws DAOException {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Tag tag = null;		
			try{
					connect = dataSource.getConnection();	
					preparedStatement = connect.prepareStatement(SHOW_TAG);
				
				preparedStatement.setLong(1, tagId);
				rs = preparedStatement.executeQuery();			
					if(rs.next()){
						tag = new Tag();
						tag.setTagId(rs.getLong("TAG_ID"));
						tag.setTagName(rs.getString("TAG_NAME"));
					}
			} catch (SQLException e) {
				throw new DAOException(e);
			} finally {
				closeResultSet(rs);
				closeConnection(connect);
				closeStatement(preparedStatement);
			}
			return tag;
	}
	
	
	public long insert(Tag tag) throws DAOException{	 
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		long tagId = 0;
		try{
			connect = dataSource.getConnection();
			preparedStatement = connect.prepareStatement(INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
	          preparedStatement.setString(1, tag.getTagName());
	          preparedStatement.executeUpdate();
	          
	          rs = preparedStatement.getGeneratedKeys();
	          if (rs.next())
	        	  tagId = rs.getLong(1);
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResultSet(rs);
			closeConnection(connect);
			closeStatement(preparedStatement);
		}
	          return tagId;
	}
	
	
	public void delete(Long tagId) throws DAOException {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try{
			connect = dataSource.getConnection();
			preparedStatement = connect.prepareStatement(DELETE_TAG);
				preparedStatement.setLong(1, tagId);
				preparedStatement.executeUpdate();
		}catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeConnection(connect);
			closeStatement(preparedStatement);
		}		
	}
	
	
	public void update(Tag tag) throws DAOException{	
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try{
			connect = dataSource.getConnection();	
			preparedStatement = connect.prepareStatement(UPDATE_TAG); 
		
		 	  preparedStatement.setString(1, tag.getTagName());
	          preparedStatement.setLong(2, tag.getTagId());
	          preparedStatement.executeUpdate();
		} catch(SQLException e){
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
