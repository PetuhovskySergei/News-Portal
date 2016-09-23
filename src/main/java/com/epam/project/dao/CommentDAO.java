package com.epam.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.epam.project.entity.Author;
import com.epam.project.entity.Comment;

public class CommentDAO {
		
	private DataSource dataSource;
	
	private final String LIST_COMMENT = "SELECT comment_id, comment_text, comment_date "
										+ "FROM comment";
	
	private final String SHOW_COMMENT = "SELECT comment_id, comment_text, comment_date "
										+ "FROM comment"
										+ "WHERE comment_id = ?";
	
	private final String INSERT_COMMENT = "INSERT INTO comment"
										+ "VALUES(default, ?, ?)";
	
	private final String DELETE_COMMENT = "DELETE FROM comment"
										+ "WHERE comment_id = ?";
	
	private final String UPDATE_COMMENT = "UPDATE comment"
										+ "SET comment_text = ?, comment_date= ?"
										+ "WHERE comment_id = ?";
	
	private final String COUNT_COMMENT = "SELECT COUNT(*) AS total FROM comment";
	
	private final String COMMENT_ID = "comment_id";
	
	
	public List<Comment> listComment() throws SQLException { 
		 List<Comment> result = new ArrayList<Comment>();
		 try{
		 Connection connect = dataSource.getConnection();	
		 Statement statement = connect.createStatement();
		 ResultSet rs = statement.executeQuery(LIST_COMMENT);
		 	while(rs.next()){
		 		Comment comment = new Comment();
		 		comment.setCommentId(rs.getLong("COMMENT_ID"));
		 		comment.setCommentText(rs.getString("COMMENT_TEXT"));
		 		comment.setCommentDate(rs.getDate("COMMENT_DATE"));
		 			result.add(comment);
		 					}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public Comment showComment(long commentId) throws SQLException {
		 
		ResultSet rs = null;
		Comment comment = new Comment();		
			try{
				Connection connect = dataSource.getConnection();	
				PreparedStatement  preparedStatement = connect.prepareStatement(SHOW_COMMENT);
				
				preparedStatement.setLong(1, commentId);
				rs = preparedStatement.executeQuery();			
					if(rs.next()){
						comment.setCommentId(rs.getLong("COMMENT_ID"));
						comment.setCommentText(rs.getString("COMMENT_TEXT"));
						comment.setCommentDate(rs.getDate("COMMENT_DATE"));
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return comment;
	}
	
	
	public long insertComment(Comment comment) throws SQLException{	 
		try{
		Connection connect = dataSource.getConnection();
		PreparedStatement preparedStatement = connect.prepareStatement(INSERT_COMMENT, Statement.RETURN_GENERATED_KEYS);
		Statement statement = connect.createStatement();
		
	          preparedStatement.setString(1, comment.getCommentText());
	          preparedStatement.setDate(2, comment.getCommentDate());
	          preparedStatement.executeUpdate();
	          
	          ResultSet rs = preparedStatement.getGeneratedKeys();
	          if (rs.next())
	        	  return rs.getLong(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	          return -1;
	}
	
	
	public void deleteComment(Long commentId) throws SQLException {

		try{
		Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(DELETE_COMMENT);
				preparedStatement.setLong(1, commentId);
				preparedStatement.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
	public void updateComment(Comment comment) throws SQLException{	 
		try{
		Connection connect = dataSource.getConnection();	
		PreparedStatement  preparedStatement = connect.prepareStatement(UPDATE_COMMENT); 
		
		 	  preparedStatement.setString(1, comment.getCommentText());
		 	  preparedStatement.setDate(2, comment.getCommentDate());
	          preparedStatement.setLong(3, comment.getCommentId());
	          preparedStatement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	public int getCountComment() throws SQLException {
		try{
			Connection connect = dataSource.getConnection();	
			PreparedStatement  preparedStatement = connect.prepareStatement(COUNT_COMMENT);
			ResultSet rs = preparedStatement.executeQuery();
		 
			List<Comment> result = new ArrayList<Comment>(); 
			 	if(rs.next()){
			 		return (rs.getInt("total"));
			 	}
				}catch(SQLException e){
					e.printStackTrace();
				}
			return -1;
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
