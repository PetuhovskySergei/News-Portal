package test.java.com.epam.project;

import static org.junit.Assert.*;

import java.sql.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.project.dao.impl.NewsDAO;
import com.epam.project.entity.News;


public class AddNewsTest {
	
	final static Logger logger = Logger.getLogger(AddNewsTest.class);

	@Test
	public void addMainTitleTest() {
		News news = new News();
		news.setMainTitle("Some main title");
		try{
			ApplicationContext context = 
		             new ClassPathXmlApplicationContext("/spring/beansXML.xml");
		      NewsDAO dao = (NewsDAO) context.getBean("newsDAO");
		      dao.insertNews(news);
			}catch(Exception e){
				logger.error(e);
			}
		assertEquals("Some main title", news.getMainTitle());
	}
	
	@Test
	public void addShotrTitleTest() {
		News news = new News();
		String s = "Some Short Title";
		news.setShortTitle(s);
		try{
			ApplicationContext context = 
		             new ClassPathXmlApplicationContext("/spring/BeansXML.xml");
		      NewsDAO dao = (NewsDAO) context.getBean("newsDAO");
		      dao.insertNews(news);
			}catch(Exception e){
				logger.error(e);
			}
		assertEquals(s, news.getShortTitle());
	}
	
	@Test
	public void addTextTest() {
		News news = new News();
		String s = "Some text";
		news.setText("Some text");
		try{
			ApplicationContext context = 
		             new ClassPathXmlApplicationContext("/spring/BeansXML.xml");
		      NewsDAO dao = (NewsDAO) context.getBean("newsDAO");
		      dao.insertNews(news);
			}catch(Exception e){
				logger.error(e);
			}
		assertEquals(s, news.getText());
	}
	
	@Test
	public void addPublishDateTest() {
		News news = new News();
		Date d = new Date(11-11-11);
		news.setPublishDate(new Date(11-11-11));
		try{
			ApplicationContext context = 
		             new ClassPathXmlApplicationContext("/spring/BeansXML.xml");
		      NewsDAO dao = (NewsDAO) context.getBean("newsDAO");
		      dao.insertNews(news);
			}catch(Exception e){
				logger.error(e);
			}
		assertEquals(d, news.getPublishDate());
	}
}	
	
