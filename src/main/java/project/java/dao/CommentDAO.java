package project.java.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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

import project.java.entity.Comment;

public class CommentDAO {
		
	ConnectionAccess connectionAccess;
	
	public ConnectionAccess getConnectionAccess() {
		return connectionAccess;
	}

	public void setConnectionAccess(ConnectionAccess connectionAccess) {
		this.connectionAccess = connectionAccess;
	}
	
	public CommentDAO() {
		
	}

	public CommentDAO(String path) throws SQLException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException{
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
	    // setup the connection with the DB.
		
		File f = new File(path);
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document document = (Document) builder.parse(f);
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
	
	public List<Comment> getComment() throws Exception {
		
		// statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement =  connectionAccess.getConnection()
	              .prepareStatement("select * from comments");//where id=parameter_value
		 ResultSet rs = preparedStatement.executeQuery();
		 
		 List<Comment> result = new ArrayList<Comment>(); 
		 	while(rs.next()){
		 		Comment comment = new Comment();
		 		comment.setCommentId(rs.getInt("COMMENT_ID"));
		 		comment.setUserId(rs.getInt("USER_ID"));
		 		comment.setNewsId(rs.getInt("NEwS_ID"));
		 		comment.setCommentText(rs.getString("COMMENT_TEXT"));
		 		comment.setCommentDate(rs.getDate("COMMENT_DATE"));
		 			result.add(comment);
			}
		return result;
	}
	
	public Comment getComment(int id) throws Exception{
		 // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection().prepareStatement("select * from comments where id=" + id);//where id=parameter_value
		 ResultSet rs = preparedStatement.executeQuery();
		   
		 if(rs.next()){
			 Comment comment = new Comment();
			 	comment.setCommentId(rs.getInt("COMMENT_ID"));
		 		comment.setUserId(rs.getInt("USER_ID"));
		 		comment.setNewsId(rs.getInt("NEWS_ID"));
		 		comment.setCommentText(rs.getString("COMMENT_TEXT"));
		 		comment.setCommentDate(rs.getDate("COMMENT_DATE"));
			 return comment;
		 }
		 return null;
	}
	
	public int insertComment(Comment comment) throws Exception{	 
	      // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection()
	              .prepareStatement("insert into  News_Portal.news values (default, ?, ?)", Statement.RETURN_GENERATED_KEYS);
	          // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	          // parameters start with 1	          
	          preparedStatement.setString(1, comment.getCommentText());
	          preparedStatement.setDate(2, comment.getCommentDate());
	          
	          int res = preparedStatement.executeUpdate();
	          
	          ResultSet rs = preparedStatement.getGeneratedKeys();
	          if (rs.next())
	        	  return rs.getInt(1);

	          return -1;
	}
	
	public void updateComment(Comment comment) throws Exception{	 
	      // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection()
	              .prepareStatement("update News_Portal.comments set comment_text=?, COMMENT_date=?  where id = ?");
	          // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	          // parameters start with 1	          
		 	  preparedStatement.setString(1, comment.getCommentText());
		 	  preparedStatement.setDate(2, comment.getCommentDate());
	          preparedStatement.setInt(3, comment.getCommentId());
	          	int res = preparedStatement.executeUpdate();	          	          
	}
	
	public int getCountComment() throws Exception {
		 // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	     // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection()
	             .prepareStatement("select count (*) as total from comments");
		 ResultSet rs = preparedStatement.executeQuery();
		 
		 List<Comment> result = new ArrayList<Comment>(); 
		 	if(rs.next()){
		 		return (rs.getInt("total"));
			}
		return -1;
	}
}
