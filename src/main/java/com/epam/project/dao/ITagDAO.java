package com.epam.project.dao;

import java.sql.SQLException;
import java.util.List;

import com.epam.project.entity.Tag;


public interface ITagDAO {
	
	public long insertTag(Tag tag) throws SQLException;
	
	public Tag showTag(long tagId) throws SQLException;;
	
	public void updateTag(Tag tag) throws SQLException;;
	
	public void deleteTag(Long tagId) throws SQLException;;
	
	public List<Tag> listTag() throws SQLException;
}

