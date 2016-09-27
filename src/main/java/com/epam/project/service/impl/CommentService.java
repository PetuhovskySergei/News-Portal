package com.epam.project.service.impl;

import java.util.List;

import com.epam.project.dao.exception.DAOException;
import com.epam.project.dao.impl.CommentDAO;
import com.epam.project.entity.Comment;
import com.epam.project.service.ICommentService;
import com.epam.project.service.exception.ServiceException;

public class CommentService implements ICommentService {
	
	private CommentDAO commentDAO;
	
	@Override
	public long insert(Comment comment) throws ServiceException {
		
		long id = 0;

		if (comment == null) {
			throw new ServiceException("Comment is null");
		}
		try {
			id = commentDAO.insert(comment);
		} catch (DAOException e) {
			throw new ServiceException("Insert was failed", e);
		}
		return id;
	}

	@Override
	public Comment show(Long id) throws ServiceException {
		
		Comment comment = null;
		try {
			comment = commentDAO.show(id);
		} catch (DAOException e) {			
			throw new ServiceException("Show was failed", e);
		}
		return comment;
	}

	@Override
	public void update(Comment comment) throws ServiceException {
		
		if (comment == null) {
			throw new ServiceException("Comment is null");
		}

		try {
			commentDAO.update(comment);
		} catch (DAOException e) {
			throw new ServiceException("Update was failed", e);
		}
	}

	@Override
	public void delete(Long id) throws ServiceException {
		
		try {
			commentDAO.delete(id);
		} catch (DAOException e) {
			throw new ServiceException("Delete was failed", e);
		}
		
	}

	@Override
	public List<Comment> list() throws ServiceException {
		
		List<Comment> list;
		try {
			list = commentDAO.list();
		} catch (DAOException e) {
			throw new ServiceException("List was failed.", e);
		}
		return list;
	}
}