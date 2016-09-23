package com.epam.project.dao;

import java.sql.SQLException;
import java.util.List;

import com.epam.project.entity.Comment;


	public interface ICommentDAO {
	
		public long insertComment(Comment comment) throws SQLException;
		
		public Comment showComment(long commentId) throws SQLException;;
		
		public void updateComment(Comment comment) throws SQLException;;
		
		public void deleteComment(Long commentId) throws SQLException;;
		
		public List<Comment> listComment() throws SQLException;
}

