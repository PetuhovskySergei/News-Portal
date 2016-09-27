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
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Comment comment = null;		
			try{
				connect = dataSource.getConnection();	
				preparedStatement = connect.prepareStatement(SHOW_COMMENT);			
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
				closeResultSet(rs);
				closeConnection(connect);
				closeStatement(preparedStatement);
			}
			return comment;
	}
	
	
	public long insert(Comment comment) throws DAOException{	
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		long commentId = 0;
			try{
				connect = dataSource.getConnection();
				preparedStatement = connect.prepareStatement(INSERT_COMMENT, Statement.RETURN_GENERATED_KEYS);
			    preparedStatement.setString(1, comment.getCommentText());
			    preparedStatement.setDate(2, comment.getCommentDate());
			    preparedStatement.executeUpdate();      
		          rs = preparedStatement.getGeneratedKeys();
		          if (rs.next())
		        	  commentId = rs.getLong(1);
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResultSet(rs);
			closeConnection(connect);
			closeStatement(preparedStatement);
		}
	          return commentId;
	}
	
	
	public List<Comment> list() throws DAOException { 
		 Connection connect = null;
  		 ResultSet rs = null;
  		 Statement statement = null;
		 List<Comment> result = new ArrayList<Comment>();
		 Comment comment = null;
		 try{
			 connect = dataSource.getConnection();	
			 statement = connect.createStatement();
			 rs = statement.executeQuery(LIST_COMMENT);
			 	while(rs.next()){
			 		comment = new Comment();
			 		comment.setCommentId(rs.getLong("COMMENT_ID"));
			 		comment.setCommentText(rs.getString("COMMENT_TEXT"));
			 		comment.setCommentDate(rs.getDate("COMMENT_DATE"));
			 			result.add(comment);
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
	
	
	public void delete(Long commentId) throws DAOException {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try{
		connect = dataSource.getConnection();
		preparedStatement = connect.prepareStatement(DELETE_COMMENT);
				preparedStatement.setLong(1, commentId);
				preparedStatement.executeUpdate();
		}catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeConnection(connect);
			closeStatement(preparedStatement);
		}	
	}
	
	
	public void update(Comment comment) throws DAOException{
		Connection connect = null;
		PreparedStatement preparedStatement = null;
			try{
				connect = dataSource.getConnection();	
				preparedStatement = connect.prepareStatement(UPDATE_COMMENT); 
			 	  preparedStatement.setString(1, comment.getCommentText());
			 	  preparedStatement.setDate(2, comment.getCommentDate());
		          preparedStatement.setLong(3, comment.getCommentId());
		          preparedStatement.executeUpdate();
		}catch(SQLException e){
			throw new DAOException(e);
		} finally {
			closeConnection(connect);
			closeStatement(preparedStatement);
		}
	}
	
	
	public int getCountComment() throws DAOException {
		Connection connect = null;
 		ResultSet rs = null;
 		PreparedStatement preparedStatement = null;
		List<Comment> result = null;
		int countComment = 0;
		try{
			connect = dataSource.getConnection();	
			preparedStatement = connect.prepareStatement(COUNT_COMMENT);
				rs = preparedStatement.executeQuery();
				result = new ArrayList<Comment>(); 
			 	if(rs.next()){
			 		countComment = (rs.getInt("total"));
			 	}
				}catch(SQLException e){
					throw new DAOException(e);
				} finally {
					closeResultSet(rs);
					closeConnection(connect);
					closeStatement(preparedStatement);
				}
			return countComment;
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
