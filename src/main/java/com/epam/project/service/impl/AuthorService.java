package com.epam.project.service.impl;

import java.util.List;

import com.epam.project.dao.exception.DAOException;
import com.epam.project.dao.impl.AuthorDAO;
import com.epam.project.entity.Author;
import com.epam.project.service.IAuthorService;
import com.epam.project.service.exception.ServiceException;

public class AuthorService implements IAuthorService {

	private AuthorDAO authorDAO;

	@Override
	public long insert(Author author) throws ServiceException {

		long id = 0;

		if (author == null) {
			throw new ServiceException("Author is null");
		}

		try {
			id = authorDAO.insert(author);
		} catch (DAOException e) {
			throw new ServiceException("Insert was failed", e);
		}
		return id;
	}

	@Override
	public Author show(Long id) throws ServiceException {

		Author author = null;
		try {
			author = authorDAO.show(id);
		} catch (DAOException e) {
			throw new ServiceException("Show was failed", e);
		}
		return author;
	}

	@Override
	public void update(Author author) throws ServiceException {

		if (author == null) {
			throw new ServiceException("Author is null");
		}

		try {
			authorDAO.update(author);
		} catch (DAOException e) {
			throw new ServiceException("Update was failed", e);
		}
	}

	@Override
	public void delete(Long id) throws ServiceException {

		try {
			authorDAO.delete(id);
		} catch (DAOException e) {
			throw new ServiceException("Delete was failed", e);
		}

	}

	@Override
	public List<Author> list() throws ServiceException {

		List<Author> list;
		try {
			list = authorDAO.list();
		} catch (DAOException e) {
			throw new ServiceException("List was failed.", e);
		}
		return list;
	}

}
