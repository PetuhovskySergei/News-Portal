package com.epam.project.service.impl;

import java.util.List;

import com.epam.project.dao.exception.DAOException;
import com.epam.project.dao.impl.TagDAO;
import com.epam.project.entity.Tag;
import com.epam.project.service.ITagService;
import com.epam.project.service.exception.ServiceException;

public class TagService implements ITagService {
	
	private TagDAO tagDAO;

	@Override
	public long insert(Tag tag) throws ServiceException {
		
		long id = 0;

		if (tag == null) {
			throw new ServiceException("Tag is null");
		}
		try {
			id = tagDAO.insert(tag);
		} catch (DAOException e) {
			throw new ServiceException("Insert was failed", e);
		}
		return id;
	}

	@Override
	public Tag show(Long id) throws ServiceException {
		
		Tag tag = null;
		try {
			tag = tagDAO.show(id);
		} catch (DAOException e) {			
			throw new ServiceException("Show was failed", e);
		}
		return tag;
	}

	@Override
	public void update(Tag tag) throws ServiceException {
		
		if (tag == null) {
			throw new ServiceException("Tag is null");
		}
		try {
			tagDAO.update(tag);
		} catch (DAOException e) {
			throw new ServiceException("Update was failed", e);
		}
	}

	@Override
	public void delete(Long id) throws ServiceException {

		try {
			tagDAO.delete(id);
		} catch (DAOException e) {
			throw new ServiceException("Delete was failed", e);
		}
	}

	@Override
	public List<Tag> list() throws ServiceException {
		
		List<Tag> list;
		try {
			list = tagDAO.list();
		} catch (DAOException e) {
			throw new ServiceException("List was failed.", e);
		}
		return list;
	}
}