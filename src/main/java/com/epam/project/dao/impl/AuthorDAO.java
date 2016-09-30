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

public class AuthorDAO implements IAuthorDAO {

	private DataSource dataSource;

	private final static String LIST_AUTHOR = "SELECT author_id, author_name" + "FROM NEWS_PORTAL.comment";

	private final static String SHOW_AUTHOR = "SELECT author_id, author_name FROM NEWS_PORTAL.author " + "WHERE author_id = ?";

	private final static String INSERT_AUTHOR = "INSERT INTO NEWS_PORTAL.author " + "VALUES(default, ?)";

	private final static String DELETE_AUTHOR = "DELETE FROM NEWS_PORTAL.author " + "WHERE author_id = ?";

	private final static String UPDATE_AUTHOR = "UPDATE NEWS_PORTAL.author " + "SET author_name = ? " + "WHERE author_id = ?";

	
	public Author show(long authorId) throws DAOException {

		ResultSet rs = null;
		Author author = null;
		try(
			Connection connect = dataSource.getConnection();
			PreparedStatement preparedStatement = connect.prepareStatement(SHOW_AUTHOR)){
					preparedStatement.setLong(1, authorId);
					rs = preparedStatement.executeQuery();			
						if (rs.next()) {
							author = new Author();
							author.setAuthorId(rs.getLong("AUTHOR_ID"));
							author.setAuthorName(rs.getString("AUTHOR_NAME"));
						}
					} catch (SQLException e) {
						throw new DAOException(e);
					}finally {
						if (rs != null)
								closeResultSet(rs);
							}
		return author;
	}

	public long insert(Author author) throws DAOException {
		
		ResultSet rs = null;
		long authorId = 0;
		try (
			Connection connect = dataSource.getConnection();
			PreparedStatement preparedStatement = connect.prepareStatement(INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS)){
				preparedStatement.setString(1, author.getAuthorName());
				preparedStatement.executeUpdate();
					rs = preparedStatement.getGeneratedKeys();
					if (rs.next())
						authorId = rs.getLong(1);
				} catch (SQLException e) {
					throw new DAOException(e);
				} finally {
					if (rs != null)
						closeResultSet(rs);
				}
		return authorId;
	}

	public List<Author> list() throws DAOException {
		
		ResultSet rs = null;
		Author author = null;
		List<Author> result = new ArrayList<Author>();
		try (
			Connection connect = dataSource.getConnection();
			Statement statement = connect.createStatement()){
					rs = statement.executeQuery(LIST_AUTHOR);
					while (rs.next()) {
						author = new Author();
						author.setAuthorId(rs.getLong("AUTHOR_ID"));
						author.setAuthorName(rs.getString("AUTHOR_NAME"));
						result.add(author);
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

	public void delete(Long authorId) throws DAOException {
		try(
			Connection connect = dataSource.getConnection();			
			PreparedStatement preparedStatement = connect.prepareStatement(DELETE_AUTHOR)){
				preparedStatement.setLong(1, authorId);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
	}

	public void update(Author author) throws DAOException {
		try (
			Connection connect = dataSource.getConnection();
			PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_AUTHOR)){
				preparedStatement.setString(1, author.getAuthorName());
				preparedStatement.setLong(2, author.getAuthorId());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
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
