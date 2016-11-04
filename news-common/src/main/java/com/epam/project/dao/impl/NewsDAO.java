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

import com.epam.project.dao.INewsDAO;
import com.epam.project.dao.exception.DAOException;
import com.epam.project.entity.Author;
import com.epam.project.entity.Comment;
import com.epam.project.entity.News;
import com.epam.project.entity.Tag;
import com.epam.project.util.Pagination;
import com.epam.project.util.QueryBuilder;
import com.epam.project.util.SearchCriteria;

@Repository
public class NewsDAO implements INewsDAO {

	@Autowired
	private DataSource dataSource;

	private final static String LIST_NEWS = "SELECT news_id, main_title, short_title, text, "
			+ "publish_date " + "FROM NEWS_PORTAL.news";

	private final static String SHOW_NEWS = "SELECT news_id, main_title, short_title, text, "
			+ "publish_date " + "FROM NEWS_PORTAL.news " + "WHERE news_id = ?";

	private final static String INSERT_NEWS = "INSERT INTO NEWS_PORTAL.news"
			+ "VALUES(default, ?, ?, ?, ?)";

	private final static String DELETE_NEWS = "DELETE FROM NEWS_PORTAL.news"
			+ "WHERE comment_id = ?";

	private final static String UPDATE_NEWS = "UPDATE NEWS_PORTAL.news"
			+ "SET main_title = ?, short_title= ?, text= ?, "
			+ "publish_date = ? " + "WHERE news_id = ?";

	private final static String SEARCH_BY_AUTHOR = "SELECT news.news_id, news.main_title, news.short_title, news.text, news.publish_date "
			+ "FROM NEWS_PORTAL.news JOIN NEWS_PORTAL.news_author "
			+ "ON news.news_id=news_author.news_id "
			+ "WHERE news_author.author_id=?";

	private final static String SEARCH_BY_TAG = "SELECT news.news_id, news.main_title, news.short_title, news.text, news.publish_date "
			+ "FROM NEWS_PORTAL.news JOIN NEWS_PORTAL.news_tag "
			+ "ON news.news_id=news_tag.news_id " + "WHERE news_tag.tag_id=?";

	private final static String UPDATE_AUTHOR_NEWS = "UPDATE NEWS_PORTAL.news_author "
			+ "SET author_id=? " + "WHERE news_id=?";

	private final static String UPDATE_TAGS_NEWS = "UPDATE NEWS_PORTAL.news_tag "
			+ "SET tag_id=? " + "WHERE news_id=?";

	private final static String GET_COMMENTS_NEWS = "SELECT comments.comment_id, comments.news_id, comments.comment_text, comments.comment_date "
			+ "FROM NEWS_PORTAL.news JOIN NEWS_PORTAL.comments ON news.news_id=comments.news_id "
			+ "WHERE comments.news_id=? ORDER BY comments.comment_date";

	private final static String GET_TAGS_NEWS = "SELECT tag.tag_id, tag.tag_name "
			+ "FROM NEWS_PORTAL.tag JOIN NEWS_PORTAL.news_tag ON tag.tag_id=news_tag.tag_id "
			+ "WHERE news_tag.news_id=?";

	private final static String GET_AUTHOR_NEWS = "SELECT author.author_id, author.author_name "
			+ "FROM NEWS_PORTAL.author JOIN NEWS_PORTAL.news_author ON author.author_id=news_author.author_id "
			+ "WHERE news_author.news_id=?";

	private final static String ADD_AUTHOR = "SELECT news.news_id, news.main_title, news.short_title, "
			+ "news.text, news.publish_date "
			+ "FROM NEWS_PORTAL.news JOIN NEWS_PORTAL.news_author "
			+ "ON news.news_id=news_author.news_id "
			+ "WHERE news_author.author_id=?";

	private final static String ADD_TAG = "INSERT INTO NEWS_PORTAL.news_tag(news_id, tag_id) "
			+ "VALUES(?, ?)";

	private final static String DELETE_AUTHOR_NEWS = "DELETE FROM NEWS_PORTAL.news_author "
			+ "WHERE news_id=?";

	private final static String DELETE_TAG_NEWS = "DELETE FROM NEWS_PORTAL.news_tag "
			+ "WHERE news_id=?";

	private final static String NEWS_ID = "NEWS_ID";

	private final static String MAIN_TITLE = "MAIN_TITLE";

	private final static String SHORT_TITLE = "SHORT_TITLE";

	private final static String TEXT = "TEXT";

	private final static String PUBLISH_DATE = "PUBLISH_DATE";

	public NewsDAO() {
	}

	public List<News> list() throws DAOException {

		ResultSet rs = null;
		News news = null;
		List<News> result = new ArrayList<News>();
		try (Connection connect = dataSource.getConnection();
				Statement statement = connect.createStatement()) {
			rs = statement.executeQuery(LIST_NEWS);
			while (rs.next()) {
				news = new News();
				news.setNewsId(rs.getLong(NEWS_ID));
				news.setMainTitle(rs.getString(MAIN_TITLE));
				news.setShortTitle(rs.getString(SHORT_TITLE));
				news.setText(rs.getString(TEXT));
				news.setPublishDate(rs.getDate(PUBLISH_DATE));
				result.add(news);
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

	public News show(long newsId) throws DAOException {

		ResultSet rs = null;
		News news = null;
		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect
						.prepareStatement(SHOW_NEWS)) {
			preparedStatement.setLong(1, newsId);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				news = new News();
				news.setNewsId(rs.getLong(NEWS_ID));
				news.setMainTitle(rs.getString(MAIN_TITLE));
				news.setShortTitle(rs.getString(SHORT_TITLE));
				news.setText(rs.getString(TEXT));
				news.setPublishDate(rs.getDate(PUBLISH_DATE));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return news;
	}

	public long insert(News news) throws DAOException {

		ResultSet rs = null;
		long newsId = 0;
		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(
						INSERT_NEWS, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, news.getMainTitle());
			preparedStatement.setString(2, news.getShortTitle());
			preparedStatement.setString(3, news.getText());
			preparedStatement.setDate(4, news.getPublishDate());
			preparedStatement.executeUpdate();

			rs = preparedStatement.getGeneratedKeys();
			if (rs.next())
				newsId = rs.getLong(1);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return newsId;
	}

	public void delete(Long newsId) throws DAOException {

		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect
						.prepareStatement(DELETE_NEWS)) {
			preparedStatement.setLong(1, newsId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void update(News news) throws DAOException {

		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect
						.prepareStatement(UPDATE_NEWS)) {
			preparedStatement.setString(1, news.getMainTitle());
			preparedStatement.setString(2, news.getShortTitle());
			preparedStatement.setString(3, news.getText());
			preparedStatement.setDate(4, news.getPublishDate());
			preparedStatement.setLong(5, news.getNewsId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Long getCountNews(SearchCriteria searchCriteria) throws DAOException {

		Long countNews = null;
		ResultSet rs = null;

		List<Long> tagsIdList = searchCriteria.getTagsIdList();
		Long authorId = searchCriteria.getAuthorId();

		String query = QueryBuilder.buildCountQuery(searchCriteria);
		try (Connection connect = dataSource.getConnection();
				PreparedStatement ps = connect.prepareStatement(query)) {

			int paramIdx = 1;
			if (authorId != null && authorId != 0) {
				ps.setLong(paramIdx++, authorId);
			}
			if (tagsIdList != null) {
				for (Long tagId : tagsIdList) {
					ps.setLong(paramIdx++, tagId);
				}
			}
			rs = ps.executeQuery();

			if (rs.next()) {
				countNews = rs.getLong(1);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL error in the search news operation.", e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return countNews;
	}

	@Override
	public void addAuthor(Long authorId, Long newsId) throws DAOException {

		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect
						.prepareStatement(ADD_AUTHOR)) {
			preparedStatement.setLong(1, newsId);
			preparedStatement.setLong(2, authorId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void addTag(Long newsId, Long tagId) throws DAOException {

		try (Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect
						.prepareStatement(ADD_TAG)) {
			preparedStatement.setLong(1, newsId);
			preparedStatement.setLong(2, tagId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void deleteAuthorNews(Long newsId) throws DAOException {

		try (Connection connect = dataSource.getConnection();
				PreparedStatement ps = connect
						.prepareStatement(DELETE_AUTHOR_NEWS)) {
			ps.setLong(1, newsId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(
					"SQL error in the delete author news operation.", e);
		}
	}

	@Override
	public void deleteTagNews(Long newsId) throws DAOException {

		try (Connection connect = dataSource.getConnection();
				PreparedStatement ps = connect
						.prepareStatement(DELETE_TAG_NEWS)) {
			ps.setLong(1, newsId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(
					"SQL error in the delete tag news operation.", e);
		}
	}

	@Override
	public List<News> searchNews(SearchCriteria searchCriteria, Integer numPage)
			throws DAOException {

		List<News> newsList = new ArrayList<>();
		News news = null;
		ResultSet rs = null;
		List<Long> tagsIdList = searchCriteria.getTagsIdList();
		Long authorId = searchCriteria.getAuthorId();
		Integer startIdx = Pagination.getStartIndex(numPage);
		Integer endIdx = Pagination.getEndIndex(numPage);
		String query = QueryBuilder.buildSearchQuery(searchCriteria);

		try (Connection connect = dataSource.getConnection();
				PreparedStatement ps = connect.prepareStatement(query)) {
			int paramIdx = 1;
			if (authorId != null && authorId != 0) {
				ps.setLong(paramIdx++, authorId);
			}
			if (tagsIdList != null) {
				for (Long tagId : tagsIdList) {
					ps.setLong(paramIdx++, tagId);
				}
			}
			ps.setInt(paramIdx++, startIdx);
			ps.setInt(paramIdx++, endIdx);
			rs = ps.executeQuery();

			while (rs.next()) {
				news = buildNews(news, rs);
				newsList.add(news);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL error in the search news operation.", e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return newsList;
	}

	@Override
	public List<News> searchByAuthor(Author author) throws DAOException {
		List<News> newsList = new ArrayList<>();
		ResultSet rs = null;
		News news = null;

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SEARCH_BY_AUTHOR)) {
			ps.setLong(1, author.getAuthorId());
			rs = ps.executeQuery();
			while (rs.next()) {
				news = new News();
				news.setNewsId(rs.getLong(1));
				news.setMainTitle(rs.getString(2));
				news.setShortTitle(rs.getString(3));
				news.setText(rs.getString(4));
				news.setPublishDate(rs.getDate(5));
				newsList.add(news);
			}
		} catch (SQLException e) {
			throw new DAOException(
					"SQL error in the search news by author operation.", e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return newsList;
	}

	@Override
	public List<News> searchByTag(Tag tag) throws DAOException {
		List<News> newsList = new ArrayList<>();
		ResultSet rs = null;
		News news = null;

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SEARCH_BY_TAG)) {
			ps.setLong(1, tag.getTagId());
			rs = ps.executeQuery();
			while (rs.next()) {
				news = new News();
				news.setNewsId(rs.getLong(1));
				news.setMainTitle(rs.getString(2));
				news.setShortTitle(rs.getString(3));
				news.setText(rs.getString(4));
				news.setPublishDate(rs.getDate(5));
				newsList.add(news);
			}
		} catch (SQLException e) {
			throw new DAOException(
					"SQL error in the search news by tag operation.", e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return newsList;
	}

	@Override
	public void updateAuthorNews(Long newsId, Long authorId)
			throws DAOException {

		try (Connection connect = dataSource.getConnection();
				PreparedStatement ps = connect
						.prepareStatement(UPDATE_AUTHOR_NEWS)) {
			ps.setLong(1, authorId);
			ps.setLong(2, newsId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(
					"SQL error in the update author news operation.", e);
		}
	}

	@Override
	public void updateTagNews(Long newsId, Long tagId) throws DAOException {

		try (Connection connect = dataSource.getConnection();
				PreparedStatement ps = connect
						.prepareStatement(UPDATE_TAGS_NEWS)) {
			ps.setLong(1, tagId);
			ps.setLong(2, newsId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(
					"SQL error in the update tags news operation.", e);
		}
	}

	@Override
	public Long getNextNews(SearchCriteria searchCriteria, Long newsId)
			throws DAOException {

		ResultSet rs = null;
		List<Long> tagsIdList = searchCriteria.getTagsIdList();
		Long authorId = searchCriteria.getAuthorId();
		Long nextNewsId = null;
		String query = QueryBuilder.buildNextQuery(searchCriteria);
		try (Connection connect = dataSource.getConnection();
				PreparedStatement ps = connect.prepareStatement(query)) {
			int paramIdx = 1;
			if (authorId != null && authorId != 0) {
				ps.setLong(paramIdx++, authorId);
			}
			if (tagsIdList != null) {
				for (Long tagId : tagsIdList) {
					ps.setLong(paramIdx++, tagId);
				}
			}
			ps.setLong(paramIdx, newsId);
			rs = ps.executeQuery();

			if (rs.next()) {
				nextNewsId = rs.getLong(1);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL error in the search news operation.", e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return nextNewsId;
	}

	@Override
	public Long getPreviousNews(SearchCriteria searchCriteria, Long newsId)
			throws DAOException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Long> tagsIdList = searchCriteria.getTagsIdList();
		Long authorId = searchCriteria.getAuthorId();
		Long nextNewsId = null;
		String query = QueryBuilder.buildPreviousQuery(searchCriteria);

		try (Connection connect = dataSource.getConnection()) {
			ps = connect.prepareStatement(query);
			int paramIdx = 1;
			if (authorId != null && authorId != 0) {
				ps.setLong(paramIdx++, authorId);
			}
			if (tagsIdList != null) {
				for (Long tagId : tagsIdList) {
					ps.setLong(paramIdx++, tagId);
				}
			}

			ps.setLong(paramIdx++, newsId);
			rs = ps.executeQuery();

			if (rs.next()) {
				nextNewsId = rs.getLong(1);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL error in the search news operation.", e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}
		return nextNewsId;
	}

	@Override
	public List<Comment> getCommentsNews(Long newsId) throws DAOException {

		ResultSet rs = null;
		Comment comment = null;
		List<Comment> commentsList = new ArrayList<>();

		try (Connection connect = dataSource.getConnection();
				PreparedStatement ps = connect
						.prepareStatement(GET_COMMENTS_NEWS)) {
			ps.setLong(1, newsId);
			rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(rs.getLong(1));
				comment.setNewsId(rs.getLong(2));
				comment.setCommentText(rs.getString(3));
				comment.setCommentDate(rs.getDate(4));
				commentsList.add(comment);
			}
		} catch (SQLException e) {
			throw new DAOException(
					"SQL error in the get comments news operation.", e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}

		return commentsList;
	}

	@Override
	public List<Tag> getTagsNews(Long newsId) throws DAOException {

		ResultSet rs = null;
		Tag tag = null;
		List<Tag> tagsList = new ArrayList<>();

		try (Connection connect = dataSource.getConnection();
				PreparedStatement ps = connect.prepareStatement(GET_TAGS_NEWS)) {
			ps.setLong(1, newsId);
			rs = ps.executeQuery();
			while (rs.next()) {
				tag = new Tag();
				tag.setTagId(rs.getLong(1));
				tag.setTagName(rs.getString(2));
				tagsList.add(tag);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL error in the get tags news operation.",
					e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}

		return tagsList;
	}

	@Override
	public Author getAuthorNews(Long newsId) throws DAOException {

		ResultSet rs = null;
		Author author = null;

		try (Connection connect = dataSource.getConnection();
				PreparedStatement ps = connect
						.prepareStatement(GET_AUTHOR_NEWS)) {
			ps.setLong(1, newsId);
			rs = ps.executeQuery();
			if (rs.next()) {
				author = new Author();
				author.setAuthorId(rs.getLong(1));
				author.setAuthorName(rs.getString(2));
			}
		} catch (SQLException e) {
			throw new DAOException(
					"SQL error in the get author news operation.", e);
		} finally {
			if (rs != null) {
				closeResultSet(rs);
			}
		}

		return author;
	}

	/**
	 * Builds news.
	 * 
	 * @param news
	 *            news
	 * @param rs
	 *            result set
	 * @return created news
	 * @throws SQLException
	 */
	private News buildNews(News news, ResultSet rs) throws SQLException {

		news = new News();
		news.setNewsId(rs.getLong(1));
		news.setMainTitle(rs.getString(2));
		news.setShortTitle(rs.getString(3));
		news.setText(rs.getString(4));
		news.setPublishDate(rs.getDate(5));
		return news;
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