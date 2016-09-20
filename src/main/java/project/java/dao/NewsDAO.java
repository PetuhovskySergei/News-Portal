package project.java.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.SAXException;

import project.java.entity.News;

public class NewsDAO {
	
	ConnectionAccess connectionAccess;
	
	
	public ConnectionAccess getConnectionAccess() {
		return connectionAccess;
	}

	public void setConnectionAccess(ConnectionAccess connectionAccess) {
		this.connectionAccess = connectionAccess;
	}
	
	public NewsDAO() {
		
	}

	public NewsDAO(String path) throws SQLException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException{
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
	    // setup the connection with the DB.
		
		File f = new File(path);
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document document = builder.parse(f);
	    Element root =  document.getDocumentElement();
	    // для простоты сразу берем message
	    Element urlElement = (Element) root.getElementsByTagName("url").item(0);
	    String url = urlElement.getTextContent();
	    Element userElement = (Element) root.getElementsByTagName("user").item(0);
	    String user = userElement.getTextContent();
	    Element passwordElement = (Element) root.getElementsByTagName("password").item(0);
	    String password = passwordElement.getTextContent();
	    
	    Connection connectionAccess = DriverManager.getConnection(url, user, password);
		}

	public List<News> getNews() throws Exception {
		 // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection()
	              .prepareStatement("select * from news");//where id=parameter_value
		 ResultSet rs = preparedStatement.executeQuery();
		 
		 List<News> result = new ArrayList<News>(); 
		 	while(rs.next()){
		 		News news = new News();
		 		news.setNewsId(rs.getInt("ID"));
		 		news.setAuthorId(rs.getInt("AUTHOR_ID"));
		 		news.setMainTitle(rs.getString("MAIN_TITLE"));
		 		news.setShortTitle(rs.getString("SHORT_TITLE"));
		 		news.setText(rs.getString("TEXT"));
		 		news.setDateOfPublishing(rs.getDate("DATE"));
		 		news.setTagId(rs.getInt("TAG_ID"));
		 		news.setCommentId(rs.getInt("COMMENT_ID"));
		 			result.add(news);
			}
		return result;
	}
	
	public News getNews(int id) throws Exception{
		 // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection().prepareStatement("select * from news where id=" + id);//where id=parameter_value
		 ResultSet rs = preparedStatement.executeQuery();
		   
		 if(rs.next()){
			 News news = new News();
			 news.setNewsId(rs.getInt("ID"));
		 		news.setMainTitle(rs.getString("MAIN_TITLE"));
		 		news.setShortTitle(rs.getString("SHORT_TITLE"));
		 		news.setText(rs.getString("TEXT"));
		 		news.setDateOfPublishing(rs.getDate("DATE"));
			 return news;
		 }
		 return null;
	}
	
	public int insertNews(News news) throws Exception{	 
	      // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection()
	              .prepareStatement("insert into  News_Portal.news values (default, ?, ? , ? , ?)", Statement.RETURN_GENERATED_KEYS);
	          // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	          // parameters start with 1	          
	          preparedStatement.setString(1, news.getMainTitle());
	          preparedStatement.setString(2, news.getShortTitle());
	          preparedStatement.setString(3, news.getText());
	          preparedStatement.setDate(4, news.getDateOfPublishing());
	          
	          int res = preparedStatement.executeUpdate();
	          
	          ResultSet rs = preparedStatement.getGeneratedKeys();
	          if (rs.next())
	        	  return rs.getInt(1);

	          return -1;
	}
	
	public void updateNews(News news) throws Exception{	 
	      // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection()
	              .prepareStatement("update News_Portal.news set maintitle=?, shorttitle=?, text=?, dateofpublishing=?  where id = ?");
	          // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	          // parameters start with 1	          
		 	preparedStatement.setString(1, news.getMainTitle());
		 	preparedStatement.setString(2, news.getShortTitle());
		 	preparedStatement.setString(3, news.getText());
		 	preparedStatement.setDate(4, news.getDateOfPublishing());
	          preparedStatement.setInt(5,news.getNewsId());
	          int res = preparedStatement.executeUpdate();	          	          
	}
	
	public void deleteNews(int id) throws Exception{
		 // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection()
	              .prepareStatement("delete from News_Portal.news where id=?");		 		
	              preparedStatement.executeUpdate();
	}
	
	public int getCountNews() throws Exception {
		 // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	     // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection()
	             .prepareStatement("select count (*) as total from news");
		 ResultSet rs = preparedStatement.executeQuery();
		 
		 List<News> result = new ArrayList<News>(); 
		 	if(rs.next()){
		 		return (rs.getInt("total"));
			}
		return -1;
	}
}
