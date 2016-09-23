package test.java.com.epam.project;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.project.dao.impl.AuthorDAO;
import com.epam.project.entity.Author;



public class AddAuthorTest {

	final static Logger logger = Logger.getLogger(AddAuthorTest.class);
	
	@Test
	public void addAuthorNameTest() {
		Author author = new Author();
		String s = "John Jackson";
		author.setAuthorName(s);
		try{
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("spring/beansXML.xml");

	      AuthorDAO dao = (AuthorDAO) context.getBean("authorDAO");
	      dao.insertAuthor(author);
		}catch(Exception e){
			logger.error(e);
		}
		assertEquals(s, author.getAuthorName());
	}
}
