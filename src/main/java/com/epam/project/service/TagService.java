package com.epam.project.service;

import java.util.List;

import com.epam.project.entity.Tag;


public interface TagService {
	List<Tag> listTag();
	Tag insertTag(Tag tag);
	void deleteTag(long tagId);
	Tag showTag(long tagId);
	void updateTag(Tag tag);
}
