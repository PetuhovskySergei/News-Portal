package com.epam.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.epam.project.entity.Comment;
import com.epam.project.entity.News;



public class NewsDAO {
	
private DataSource dataSource;
	
	private final String LIST_NEWS = "SELECT news_id, main_title, short_title, text, "
										+ "publish_date "
										+ "FROM news";
	
	private final String SHOW_NEWS = "news_id, main_title, short_title, text, "
										+ "publish_date "
										+ "FROM news "
										+ "WHERE news_id = ?";
	
	private final String INSERT_NEWS = "INSERT INTO news"
										+ "VALUES(default, ?, ?, ?, ?)";
	
	private final String DELETE_NEWS = "DELETE FROM news"
										+ "WHERE comment_id = ?";
	
	private final String UPDATE_NEWS = "UPDATE news"
										+ "SET main_title = ?, short_title= ?, text= ?, "
										+ "publish_date = ? "
										+ "WHERE news_id = ?";
	
	private final String COUNT_NEWS = "SELECT COUNT(*) AS total FROM news";
	
	private final String NEWS_ID = "news_id";

	
	public List<News> listNews() throws SQLException { 
		 List<News> result = new ArrayList<News>();
		 try{
		 Connection connect = dataSource.getConnection();	
		 Statement statement = connect.createStatement();
		 ResultSet rs = statement.executeQuery(LIST_NEWS);
		 	while(rs.next()){
		 		News news = new News();
		 		news.setNewsId(rs.getLong("NEWS_ID"));
		 		news.setMainTitle(rs.getString("MAIN_TITLE"));
		 		news.setShortTitle(rs.getString("SHORT_TITLE"));
		 		news.setText(rs.getString("TEXT"));
		 		news.setPublishDate(rs.getDate("PUBLISH_DATE"));
		 			result.add(news);
		 					}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public News showNews(long newsId) throws SQLException {
		 
		ResultSet rs = null;
		News news = new News();		
			try{
				Connection connect = dataSource.getConnection();	
				PreparedStatement  preparedStatement = connect.prepareStatement(SHOW_NEWS);
				
				preparedStatement.setLong(1, newsId);
				rs = preparedStatement.executeQuery();			
					if(rs.next()){
						news.setNewsId(rs.getLong("NEWS_ID"));
						news.setMainTitle(rs.getString("MAIN_TITLE"));
						news.setShortTitle(rs.getString("SHORT_TITLE"));
						news.setText(rs.getString("TEXT"));
						news.setPublishDate(rs.getDate("PUBLISH_DATE"));
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return news;
	}
	
	
	public long insertNews(News news) throws SQLException{	 
		try{
		Connection connect = dataSource.getConnection();
		PreparedStatement preparedStatement = connect.prepareStatement(INSERT_NEWS, Statement.RETURN_GENERATED_KEYS);
		Statement statement = connect.createStatement();
		
	          preparedStatement.setString(1, news.getMainTitle());
	          preparedStatement.setString(2, news.getShortTitle());
	          preparedStatement.setString(3, news.getText());
	          preparedStatement.setDate(4, news.getPublishDate());
	          preparedStatement.executeUpdate();
	          
	          ResultSet rs = preparedStatement.getGeneratedKeys();
	          if (rs.next())
	        	  return rs.getLong(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	          return -1;
	}
	
	
	public void deleteNews(Long newsId) throws SQLException {

		try{
		Connection connect = dataSource.getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(DELETE_NEWS);
				preparedStatement.setLong(1, newsId);
				preparedStatement.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
	public void updateNews(News news) throws SQLException{	 
		try{
		Connection connect = dataSource.getConnection();	
		PreparedStatement  preparedStatement = connect.prepareStatement(UPDATE_NEWS); 
		
		 	  preparedStatement.setString(1, news.getMainTitle());
		 	  preparedStatement.setString(2, news.getShortTitle());
		 	  preparedStatement.setString(3, news.getText());
		 	  preparedStatement.setDate(4, news.getPublishDate());
	          preparedStatement.setLong(5, news.getNewsId());
	          preparedStatement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	public int getCountNews() throws SQLException {
		try{
			Connection connect = dataSource.getConnection();	
			PreparedStatement  preparedStatement = connect.prepareStatement(COUNT_NEWS);
			ResultSet rs = preparedStatement.executeQuery();
		 
			List<News> result = new ArrayList<News>(); 
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
