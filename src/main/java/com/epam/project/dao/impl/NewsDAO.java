package com.epam.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.epam.project.dao.INewsDAO;
import com.epam.project.dao.exception.DAOException;
import com.epam.project.entity.Comment;
import com.epam.project.entity.News;
import com.epam.project.entity.Tag;



public class NewsDAO implements INewsDAO {
	
private DataSource dataSource;
	
	private final static String LIST_NEWS = "SELECT news_id, main_title, short_title, text, "
										+ "publish_date "
										+ "FROM news";
	
	private final static String SHOW_NEWS = "news_id, main_title, short_title, text, "
										+ "publish_date "
										+ "FROM news "
										+ "WHERE news_id = ?";
	
	private final static String INSERT_NEWS = "INSERT INTO news"
										+ "VALUES(default, ?, ?, ?, ?)";
	
	private final static String DELETE_NEWS = "DELETE FROM news"
										+ "WHERE comment_id = ?";
	
	private final static String UPDATE_NEWS = "UPDATE news"
										+ "SET main_title = ?, short_title= ?, text= ?, "
										+ "publish_date = ? "
										+ "WHERE news_id = ?";
	
	private final static String COUNT_NEWS = "SELECT COUNT(*) AS total FROM news";
	
	private final static String ADD_AUTHOR = "SELECT news.news_id, news.main_title, news.short_title, "
											+ "news.text, news.publish_date "
											+ "FROM news JOIN news_author "
											+ "ON news.news_id=news_author.news_id "
											+ "WHERE news_author.author_id=?";
	
	private final static String ADD_TAG = "INSERT INTO news_tag(news_id, tag_id) "
										+ "VALUES(?, ?)";
	
	private final static String SEARCH_TAG = "SELECT news.news_id, news.main_title, news.short_title, "
											+ "news.text, news.publish_date "
											+ "FROM news JOIN news_tag "
											+ "ON news.news_id=news_tag.news_id "
											+ "WHERE news_tag.tag_id=?";
	
	private final static String NEWS_ID = "news_id";

	
	public List<News> list() throws DAOException { 
		 Connection connect = null;
 		 ResultSet rs = null;
 		 Statement statement = null;
		 News news = null;
		 List<News> result = new ArrayList<News>();
		 try{
			 connect = dataSource.getConnection();	
			 statement = connect.createStatement();
			 rs = statement.executeQuery(LIST_NEWS);
		 	while(rs.next()){
		 		news = new News();
		 		news.setNewsId(rs.getLong("NEWS_ID"));
		 		news.setMainTitle(rs.getString("MAIN_TITLE"));
		 		news.setShortTitle(rs.getString("SHORT_TITLE"));
		 		news.setText(rs.getString("TEXT"));
		 		news.setPublishDate(rs.getDate("PUBLISH_DATE"));
		 			result.add(news);
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
	
	
	public News show(long newsId) throws DAOException {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		News news = null;		
			try{
				connect = dataSource.getConnection();	
				preparedStatement = connect.prepareStatement(SHOW_NEWS);
					preparedStatement.setLong(1, newsId);
					rs = preparedStatement.executeQuery();			
						if(rs.next()){
							news = new News();
							news.setNewsId(rs.getLong("NEWS_ID"));
							news.setMainTitle(rs.getString("MAIN_TITLE"));
							news.setShortTitle(rs.getString("SHORT_TITLE"));
							news.setText(rs.getString("TEXT"));
							news.setPublishDate(rs.getDate("PUBLISH_DATE"));
					}
			} catch (SQLException e) {
				throw new DAOException(e);
			}finally {
				closeResultSet(rs);
				closeConnection(connect);
				closeStatement(preparedStatement);
			}
			return news;
	}
	
	
	public long insert(News news) throws DAOException{	 
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		long newsId = 0;
		try{
			connect = dataSource.getConnection();
			preparedStatement = connect.prepareStatement(INSERT_NEWS, Statement.RETURN_GENERATED_KEYS);
	          preparedStatement.setString(1, news.getMainTitle());
	          preparedStatement.setString(2, news.getShortTitle());
	          preparedStatement.setString(3, news.getText());
	          preparedStatement.setDate(4, news.getPublishDate());
	          preparedStatement.executeUpdate();
	          
	          rs = preparedStatement.getGeneratedKeys();
	          if (rs.next())
	        	  newsId = rs.getLong(1);
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResultSet(rs);
			closeConnection(connect);
			closeStatement(preparedStatement);
		}
	          return newsId;
	}
	
	
	public void delete(Long newsId) throws DAOException {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
			try{
				connect = dataSource.getConnection();
					preparedStatement = connect.prepareStatement(DELETE_NEWS);
					preparedStatement.setLong(1, newsId);
					preparedStatement.executeUpdate();
			}catch (SQLException e) {
				throw new DAOException(e);
			} finally {
				closeConnection(connect);
				closeStatement(preparedStatement);
			}		
		}
	
	
	public void update(News news) throws DAOException{	
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try{
		connect = dataSource.getConnection();	
		preparedStatement = connect.prepareStatement(UPDATE_NEWS); 
		 	  preparedStatement.setString(1, news.getMainTitle());
		 	  preparedStatement.setString(2, news.getShortTitle());
		 	  preparedStatement.setString(3, news.getText());
		 	  preparedStatement.setDate(4, news.getPublishDate());
	          preparedStatement.setLong(5, news.getNewsId());
	          preparedStatement.executeUpdate();
		}catch(SQLException e){
			throw new DAOException(e);
		} finally {
			closeConnection(connect);
			closeStatement(preparedStatement);
		}
	}
	
	
	public int getCountNews() throws DAOException {
		Connection connect = null;
 		ResultSet rs = null;
 		PreparedStatement preparedStatement = null;
		List<News> result = null;
		int countNews = 0;
		try{
			connect = dataSource.getConnection();	
			preparedStatement = connect.prepareStatement(COUNT_NEWS);
			rs = preparedStatement.executeQuery();
		 
			result = new ArrayList<News>(); 
			 	if(rs.next()){
			 		countNews = (rs.getInt("total"));
			 	}
				} catch(SQLException e){
					throw new DAOException(e);
				} finally {
					closeResultSet(rs);
					closeConnection(connect);
					closeStatement(preparedStatement);
				}
			return countNews;
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


	@Override
	public void addAuthor(Long authorId, Long newsId) throws DAOException {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try{
			connect = dataSource.getConnection();
			preparedStatement = connect.prepareStatement(ADD_AUTHOR);
			preparedStatement.setLong(1, newsId);
			preparedStatement.setLong(2, authorId);
			preparedStatement.executeUpdate();
		}catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeConnection(connect);
			closeStatement(preparedStatement);
		}
	}


	@Override
	public void addTag(Long newsId, Long tagId) throws DAOException {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try{
			connect = dataSource.getConnection(); 
			preparedStatement = connect.prepareStatement(ADD_TAG);
			preparedStatement.setLong(1, newsId);
			preparedStatement.setLong(2, tagId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeConnection(connect);
			closeStatement(preparedStatement);
		}
	}


	public List<News> searchTag(Tag tag) throws DAOException {
		List<News> list = new ArrayList<>();
		ResultSet rs = null;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		News news = null;
		
		try{
			connect = dataSource.getConnection();
			preparedStatement = connect.prepareStatement(SEARCH_TAG);
			preparedStatement.setLong(1, tag.getTagId());
			rs = preparedStatement.executeQuery();
			while(rs.next()){
				news = new News();
				news.setNewsId(rs.getLong(1));
				news.setMainTitle(rs.getString(2));
				news.setShortTitle(rs.getString(3));
				news.setText(rs.getString(4));
				news.setPublishDate(rs.getDate(5));
				list.add(news);
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResultSet(rs);
			closeConnection(connect);
			closeStatement(preparedStatement);
		}
		return list;
	}
}
