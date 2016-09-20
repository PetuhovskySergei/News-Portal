package project.java.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.annotation.Bean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import  project.java.entity.Author;


public class AuthorDAO {
	
	ConnectionAccess connectionAccess;
	
	
		
	public ConnectionAccess getConnectionAccess() {
		return connectionAccess;
	}
	
	public void setConnectionAccess(ConnectionAccess connectionAccess) {
		this.connectionAccess = connectionAccess;
	}
	
	public AuthorDAO(){
		
	}
	
	public AuthorDAO(String path) throws SQLException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException{
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

	public Author getAuthor(int id) throws Exception{
		 // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection().prepareStatement("select * from author where id=" + id);//where id=parameter_value
		 ResultSet rs = preparedStatement.executeQuery();
		   
		 if(rs.next()){
			 Author author = new Author();
			 author.setAuthorId(rs.getInt("AUTHOR_ID"));
			 author.setAuthorName(rs.getString("AUTHOR_NAME"));
			 return author;
		 }
		 return null;
	}
	
	public int insertAuthor(Author author) throws Exception{	 
	      // statements allow to issue SQL queries to the database
		 Statement  statement = ConnectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = ConnectionAccess.getConnection()
	              .prepareStatement("insert into  News_Portal.author values (default, ?)", Statement.RETURN_GENERATED_KEYS);
	          // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	          // parameters start with 1	          
	          preparedStatement.setString(1, author.getAuthorName());
	          
	          int res = preparedStatement.executeUpdate();
	          
	          ResultSet rs = preparedStatement.getGeneratedKeys();
	          if (rs.next())
	        	  return rs.getInt(1);

	          return -1;
	}
	
	public void updateAuthor(Author author) throws Exception{	 
	      // statements allow to issue SQL queries to the database
		 Statement  statement = ConnectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = ConnectionAccess.getConnection()
	              .prepareStatement("update News_Portal.author set author_name=?  where id = ?");
	          // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	          // parameters start with 1	          
		 	  preparedStatement.setString(1, author.getAuthorName());
	          preparedStatement.setInt(2, author.getAuthorId());
	          	int res = preparedStatement.executeUpdate();	          	          
	}
}
