package com.epam.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.project.dao.ICommentDAO;
import com.epam.project.dao.exception.DAOException;
import com.epam.project.entity.Comment;

@Repository
public class CommentDAO implements ICommentDAO {

	@Autowired
	private DataSource dataSource;

	private final static String LIST_COMMENT = "SELECT comment_id, comment_text, comment_date "
			+ "FROM NEWS_PORTAL.comment";

	private final static String SHOW_COMMENT = "SELECT comment_id, comment_text, comment_date "
			+ "FROM NEWS_PORTAL.comment" + "WHERE comment_id = ?";

	private final static String INSERT_COMMENT = "INSERT INTO NEWS_PORTAL.comment"
			+ "VALUES(default, ?, ?)";

	private final static String DELETE_COMMENT = "DELETE FROM NEWS_PORTAL.comment"
			+ "WHERE comment_id = ?";

	private final static String UPDATE_COMMENT = "UPDATE NEWS_PORTAL.comment"
			+ "SET comment_text = ?, comment_date= ?" + "WHERE comment_id = ?";

	private final static String COUNT_COMMENT = "SELECT COUNT(*) AS total FROM NEWS_PORTAL.comment";

	private final static String COMMENT_ID = "COMMENT_ID";

	private final static String COMMENT_TEXT = "COMMENT_TEXT";

	private final static String COMMENT_DATE = "COMMENT_DATE";

	public CommentDAO() {
	}

	public Comment show(long commentId) throws DAOException {

		ResultSet rs = null;
		Comment comment = null;
		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect
						.prepareStatement(SHOW_COMMENT)) {
			preparedStatement.setLong(1, commentId);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				comment = new Comment();
				comment.setCommentId(rs.getLong(COMMENT_ID));
				comment.setCommentText(rs.getString(COMMENT_TEXT));
				comment.setCommentDate(rs.getDate(COMMENT_DATE));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return comment;
	}

	public long insert(Comment comment) throws DAOException {
		ResultSet rs = null;
		long commentId = 0;
		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(
						INSERT_COMMENT, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, comment.getCommentText());
			preparedStatement.setDate(2, comment.getCommentDate());
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next())
				commentId = rs.getLong(1);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return commentId;
	}

	public List<Comment> list() throws DAOException {
		ResultSet rs = null;
		List<Comment> result = new ArrayList<Comment>();
		Comment comment = null;
		try (Connection connect = dataSource.getConnection();
				Statement statement = connect.createStatement()) {
			rs = statement.executeQuery(LIST_COMMENT);
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(rs.getLong(COMMENT_ID));
				comment.setCommentText(rs.getString(COMMENT_TEXT));
				comment.setCommentDate(rs.getDate(COMMENT_DATE));
				result.add(comment);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return result;
	}

	public void delete(Long commentId) throws DAOException {
		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect
						.prepareStatement(DELETE_COMMENT)) {
			preparedStatement.setLong(1, commentId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void update(Comment comment) throws DAOException {
		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect
						.prepareStatement(UPDATE_COMMENT)) {
			preparedStatement.setString(1, comment.getCommentText());
			preparedStatement.setDate(2, comment.getCommentDate());
			preparedStatement.setLong(3, comment.getCommentId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public int getCountComment() throws DAOException {
		ResultSet rs = null;
		int countComment = 0;
		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect
						.prepareStatement(COUNT_COMMENT)) {
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				countComment = (rs.getInt("total"));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return countComment;
	}

	private void closeResultSet(ResultSet rs) throws DAOException {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DAOException("Could not close ResultSet", e);
			}
		}
	}
}
