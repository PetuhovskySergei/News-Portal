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


import project.java.entity.Tag;
public class TagDAO {

	ConnectionAccess connectionAccess; 
	
	
	public ConnectionAccess getConnectionAccess() {
		return connectionAccess;
	}

	public void setConnectionAccess(ConnectionAccess connectionAccess) {
		this.connectionAccess = connectionAccess;
	}
	
	public TagDAO(){
		
	}

	public TagDAO(String path) throws SQLException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException{
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
	
	public List<Tag> getTag() throws Exception {
		 // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection()
	              .prepareStatement("select * from tag");//where id=parameter_value
		 ResultSet RS = preparedStatement.executeQuery();
		 
		 List<Tag> result = new ArrayList<Tag>(); 
		 	while(RS.next()){
		 		Tag tag = new Tag();
		 		tag.setTagId(RS.getInt("ID"));
		 		tag.setTagName(RS.getString("TAG"));
		 			result.add(tag);
			}
		return result;
	}
	
	public Tag getTag(int id) throws Exception{
		 // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection().prepareStatement("select * from tag where id=" + id);//where id=parameter_value
		 ResultSet RS = preparedStatement.executeQuery();
		   
		 if(RS.next()){
			 Tag tag = new Tag();
			 	tag.setTagId(RS.getInt("ID"));
		 		tag.setTagName(RS.getString("TAG"));
			 return tag;
		 }
		 return null;
	}
	
	public int insertTag(Tag tag) throws Exception{	 
	      // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection()
	              .prepareStatement("insert into  News_Portal.tag values (default, ?)", Statement.RETURN_GENERATED_KEYS);
	          // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	          // parameters start with 1	          
	          preparedStatement.setString(1, tag.getTagName());
	          
	          int res = preparedStatement.executeUpdate();
	          
	          ResultSet rs = preparedStatement.getGeneratedKeys();
	          if (rs.next())
	        	  return rs.getInt(1);

	          return -1;
	}
	
	public void updateTag(Tag tag) throws Exception{	 
	      // statements allow to issue SQL queries to the database
		 Statement  statement = connectionAccess.getConnection().createStatement();
	      // resultSet gets the result of the SQL query
		 PreparedStatement  preparedStatement = connectionAccess.getConnection()
	              .prepareStatement("update News_Portal.tag set tagname=?  where id = ?");
	          // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	          // parameters start with 1	          
		 	  preparedStatement.setString(1, tag.getTagName());
	          preparedStatement.setInt(2, tag.getTagId());
	          	int res = preparedStatement.executeUpdate();	          	          
	}
}
