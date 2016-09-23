package test.java.test.java;

import static org.junit.Assert.*;

import java.sql.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import project.java.dao.CommentDAO;
import project.java.entity.Comment;

public class AddCommentTest {
	
	final static Logger logger = Logger.getLogger(AddCommentTest.class);

	@Test
	public void AddCommentTextTest() {
		Comment comment = new Comment();
		String s = "Some Text";
		comment.setCommentText(s);
		try{
			ApplicationContext context = 
		             new ClassPathXmlApplicationContext("/spring/beansXML.xml");
		      CommentDAO dao = (CommentDAO) context.getBean("commentDAO");
		      dao.insertComment(comment);
			}catch(Exception e){
				logger.error(e);
			}
		assertEquals(s, comment.getCommentText());
	}
	
	@Test
	public void AddCommentDateTest() {
		Comment comment = new Comment();
		Date d = new Date(11-11-11);
		comment.setCommentDate(d);
		try{
			ApplicationContext context = 
		             new ClassPathXmlApplicationContext("/spring/BeansXML.xml");
		      CommentDAO dao = (CommentDAO) context.getBean("commentDAO");
		      dao.insertComment(comment);
			}catch(Exception e){
				logger.error(e);
			}
		assertEquals(d, comment.getCommentDate());
	}
}
