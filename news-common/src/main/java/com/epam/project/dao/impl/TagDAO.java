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

import com.epam.project.dao.ITagDAO;
import com.epam.project.dao.exception.DAOException;
import com.epam.project.entity.Tag;

@Repository
public class TagDAO implements ITagDAO {

	@Autowired
	private DataSource dataSource;

	private final static String LIST_TAG = "SELECT tag_id, tag_name "
			+ "FROM NEWS_PORTAL.tag";

	private final static String SHOW_TAG = "SELECT tag_id, tag_name "
			+ "FROM NEWS_PORTAL.tag" + "WHERE tag_id = ?";

	private final static String INSERT_TAG = "INSERT INTO NEWS_PORTAL.tag"
			+ "VALUES(default, ?)";

	private final static String DELETE_TAG = "DELETE FROM NEWS_PORTAL.tag"
			+ "WHERE tag_id = ?";

	private final static String UPDATE_TAG = "UPDATE NEWS_PORTAL.tag"
			+ "SET tag_name = ?" + "WHERE tag_id = ?";

	private final static String TAG_ID = "TAG_ID";

	private final static String TAG_NAME = "TAG_NAME";

	public TagDAO() {
	}

	public List<Tag> list() throws DAOException {

		ResultSet rs = null;
		Tag tag = null;
		List<Tag> result = new ArrayList<Tag>();
		try (Connection connect = dataSource.getConnection();
				Statement statement = connect.createStatement()) {
			rs = statement.executeQuery(LIST_TAG);
			while (rs.next()) {
				tag = new Tag();
				tag.setTagId(rs.getLong(TAG_ID));
				tag.setTagName(rs.getString(TAG_NAME));
				result.add(tag);
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

	public Tag show(long tagId) throws DAOException {

		ResultSet rs = null;
		Tag tag = null;
		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect
						.prepareStatement(SHOW_TAG)) {
			preparedStatement.setLong(1, tagId);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				tag = new Tag();
				tag.setTagId(rs.getLong(TAG_ID));
				tag.setTagName(rs.getString(TAG_NAME));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return tag;
	}

	public long insert(Tag tag) throws DAOException {

		ResultSet rs = null;
		long tagId = 0;
		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(
						INSERT_TAG, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, tag.getTagName());
			preparedStatement.executeUpdate();

			rs = preparedStatement.getGeneratedKeys();
			if (rs.next())
				tagId = rs.getLong(1);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return tagId;
	}

	public void delete(Long tagId) throws DAOException {

		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect
						.prepareStatement(DELETE_TAG)) {
			preparedStatement.setLong(1, tagId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void update(Tag tag) throws DAOException {

		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect
						.prepareStatement(UPDATE_TAG)) {
			preparedStatement.setString(1, tag.getTagName());
			preparedStatement.setLong(2, tag.getTagId());
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
