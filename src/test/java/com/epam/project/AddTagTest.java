package test.java.test.java;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import project.java.dao.TagDAO;
import project.java.entity.Tag;

public class AddTagTest {
	
	final static Logger logger = Logger.getLogger(AddTagTest.class);

	@Test
	public void addTagNameTest() {
		Tag tag = new Tag();
		String s = "Tag name";
		tag.setTagName(s);
		try{
			ApplicationContext context = 
		             new ClassPathXmlApplicationContext("/spring/beansXML.xml");
		      TagDAO dao = (TagDAO) context.getBean("tagDAO");
		      dao.insertTag(tag);
			}catch(Exception e){
				logger.error(e);
			}
		assertEquals(s, tag.getTagName());
	}

}
