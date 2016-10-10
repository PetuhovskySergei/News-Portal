package test.java.com.epam.project.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.epam.project.dao.impl.AuthorDAO;
import com.epam.project.entity.Author;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:dataSource-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
@DatabaseSetup("/test/databaseDataset.xml")

public class AuthorDAOTest {

	@Autowired
	private AuthorDAO authorDAO;
	
	
	@Test
	public void createAuthorTest() throws Exception {

		Author author = new Author();
		author.setAuthorName("Sergei");

		long id = authorDAO.insert(author);
		Assert.assertEquals(author.getAuthorName(), authorDAO.show(id).getAuthorName());

	}
	
	@Test
	public void authorListTest() throws Exception {
		
		List<Author> authorsList = authorDAO.list();
		Assert.assertEquals(3, authorsList.size());
	}


}
