package com.epam.project.service.impl;

import java.util.List;

import com.epam.project.entity.Tag;
import com.epam.project.repository.TagRepository;
import com.epam.project.service.TagService;



public class TagServiceImpl implements TagService {
	
	private TagRepository tagRepository;

	@Override
	public Tag insertTag(Tag tag) {
		
		Tag savedTag = tagRepository.saveAndFlush(tag);
		 
        return savedTag;
	}

	@Override
	public void deleteTag(long tagId) {
		
		tagRepository.delete(tagId);		
	}

	@Override
	public Tag showTag(long tagId) {
		
		return tagRepository.findOne(tagId);
	}

	@Override
	public void updateTag(Tag tag) {
		
		  tagRepository.saveAndFlush(tag);
		
	}

	@Override
	public List<Tag> listTag() {
		return tagRepository.findAll();
	}
}
