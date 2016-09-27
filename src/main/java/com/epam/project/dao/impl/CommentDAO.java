package com.epam.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.epam.project.dao.ICommentDAO;
import com.epam.project.dao.exception.DAOException;
import com.epam.project.entity.Comment;

public class CommentDAO implements ICommentDAO {
		
	private DataSource dataSource;
	
	private final static String LIST_COMMENT = "SELECT comment_id, comment_text, comment_date "
										+ "FROM comment";
	
	private final static String SHOW_COMMENT = "SELECT comment_id, comment_text, comment_date "
										+ "FROM comment"
										+ "WHERE comment_id = ?";
	
	private final static String INSERT_COMMENT = "INSERT INTO comment"
										+ "VALUES(default, ?, ?)";
	
	private final static String DELETE_COMMENT = "DELETE FROM comment"
										+ "WHERE comment_id = ?";
	
	private final static String UPDATE_COMMENT = "UPDATE comment"
										+ "SET comment_text = ?, comment_date= ?"
										+ "WHERE comment_id = ?";
	
	private final static String COUNT_COMMENT = "SELECT COUNT(*) AS total FROM comment";
	
	private final static String COMMENT_ID = "comment_id";
	

	public Comment show(long commentId) throws DAOException {
		 
		ResultSet rs = null;
		Comment comment = null;		
			try(
				Connection connect = dataSource.getConnection();	
				PreparedStatement  preparedStatement = connect.prepareStatement(SHOW_COMMENT)){			
						preparedStatement.setLong(1, commentId);
						rs = preparedStatement.executeQuery();			
					if(rs.next()){
						comment = new Comment();
						comment.setCommentId(rs.getLong("COMMENT_ID"));
						comment.setCommentText(rs.getString("COMMENT_TEXT"));
						comment.setCommentDate(rs.getDate("COMMENT_DATE"));
					}
			} catch (SQLException e) {
				throw new DAOException(e); 
			} finally {
				close(rs);
			}
			return comment;
	}
	
	
	public long insert(Comment comment) throws DAOException{	
		ResultSet rs = null;
		long commentId = 0;
		try(
		Connection connect = dataSource.getConnection();
		PreparedStatement preparedStatement = connect.prepareStatement(INSERT_COMMENT, Statement.RETURN_GENERATED_KEYS);
		Statement statement = connect.createStatement()){
		
	          preparedStatement.setString(1, comment.getCommentText());
	          preparedStatement.setDate(2, comment.getCommentDate());
	          preparedStatement.executeUpdate();
	          
	          rs = preparedStatement.getGeneratedKeys();
	          if (rs.next())
	        	  commentId = rs.getLong(1);
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			close(rs);
		}
	          return commentId;
	}
	
	
	public List<Comment> list() throws DAOException { 
		 List<Comment> result = new ArrayList<Comment>();
		 Comment comment = null;
		 try(
		 Connection connect = dataSource.getConnection();	
		 Statement statement = connect.createStatement();
		 ResultSet rs = statement.executeQuery(LIST_COMMENT)){
		 	while(rs.next()){
		 		comment = new Comment();
		 		comment.setCommentId(rs.getLong("COMMENT_ID"));
		 		comment.setCommentText(rs.getString("COMMENT_TEXT"));
		 		comment.setCommentDate(rs.getDate("COMMENT_DATE"));
		 			result.add(comment);
		 					}
		}catch(SQLException e){
			throw new DAOException(e);
		}
		return result;
	}
	
	
	public void delete(Long commentId) throws DAOException {

		try(
		Connection connect = dataSource.getConnection();
		PreparedStatement preparedStatement = connect.prepareStatement(DELETE_COMMENT)){
				preparedStatement.setLong(1, commentId);
				preparedStatement.executeUpdate();
		}catch (SQLException e) {
			throw new DAOException(e);
		}	
	}
	
	
	public void update(Comment comment) throws DAOException{	 
		try(
		Connection connect = dataSource.getConnection();	
		PreparedStatement  preparedStatement = connect.prepareStatement(UPDATE_COMMENT)){ 
		 	  preparedStatement.setString(1, comment.getCommentText());
		 	  preparedStatement.setDate(2, comment.getCommentDate());
	          preparedStatement.setLong(3, comment.getCommentId());
	          preparedStatement.executeUpdate();
		}catch(SQLException e){
			throw new DAOException(e);
		}
	}
	
	
	public int getCountComment() throws DAOException {
		List<Comment> result = null;
		int countComment = 0;
		try(
			Connection connect = dataSource.getConnection();	
			PreparedStatement  preparedStatement = connect.prepareStatement(COUNT_COMMENT)){
			ResultSet rs = preparedStatement.executeQuery();
		 
			result = new ArrayList<Comment>(); 
			 	if(rs.next()){
			 		countComment = (rs.getInt("total"));
			 	}
				}catch(SQLException e){
					throw new DAOException(e);
				}
			return countComment;
	}
	
	
	private void close(ResultSet rs) throws DAOException{
			
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DAOException(e);
				}
			}
		}
}
