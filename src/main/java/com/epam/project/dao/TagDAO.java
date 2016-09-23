package com.epam.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.epam.project.entity.Tag;

public class TagDAO {
	
private DataSource dataSource;
	
	private final String LIST_TAG = "SELECT tag_id, tag_name "
										+ "FROM tag";
	
	private final String SHOW_TAG = "SELECT tag_id, tag_name "
										+ "FROM tag"
										+ "WHERE tag_id = ?";
	
	private final String INSERT_TAG = "INSERT INTO tag"
										+ "VALUES(default, ?)";
	
	private final String DELETE_TAG = "DELETE FROM tag"
										+ "WHERE tag_id = ?";
	
	private final String UPDATE_TAG = "UPDATE tag"
										+ "SET tag_name = ?"
										+ "WHERE tag_id = ?";
	
	public List<Tag> listTag() throws SQLException { 
		 List<Tag> result = new ArrayList<Tag>();
		 try{
		 Connection connect = dataSource.getConnection();	
		 Statement statement = connect.createStatement();
		 ResultSet rs = statement.executeQuery(LIST_TAG);
		 	while(rs.next()){
		 		Tag tag = new Tag();
		 		tag.setTagId(rs.getLong("TAG_ID"));
		 		tag.setTagName(rs.getString("TAG_NAME"));
		 			result.add(tag);
		 					}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public Tag showTag(long tagId) throws SQLException {
		 
		ResultSet rs = null;
		Tag tag = new Tag();		
			try{
				Connection connect = dataSource.getConnection();	
				PreparedStatement  preparedStatement = connect.prepareStatement(SHOW_TAG);
				
				preparedStatement.setLong(1, tagId);
				rs = preparedStatement.executeQuery();			
					if(rs.next()){
						tag.setTagId(rs.getLong("TAG_ID"));
						tag.setTagName(rs.getString("TAG_NAME"));
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return tag;
	}
	
	
	public long insertTag(Tag tag) throws SQLException{	 
		try{
		Connection connect = dataSource.getConnection();
		PreparedStatement preparedStatement = connect.prepareStatement(INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
		Statement statement = connect.createStatement();
		
	          preparedStatement.setString(1, tag.getTagName());
	          preparedStatement.executeUpdate();
	          
	          ResultSet rs = preparedStatement.getGeneratedKeys();
	          if (rs.next())
	        	  return rs.getLong(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	          return -1;
	}
	
	
	public void deleteTag(Long tagId) throws SQLException {

		try{
		Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(DELETE_TAG);
				preparedStatement.setLong(1, tagId);
				preparedStatement.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
	public void updateTag(Tag tag) throws SQLException{	 
		try{
		Connection connect = dataSource.getConnection();	
		PreparedStatement  preparedStatement = connect.prepareStatement(UPDATE_TAG); 
		
		 	  preparedStatement.setString(1, tag.getTagName());
	          preparedStatement.setLong(2, tag.getTagId());
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
