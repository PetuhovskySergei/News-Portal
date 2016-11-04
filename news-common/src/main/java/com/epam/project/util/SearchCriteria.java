package com.epam.project.util;

import java.util.List;

public class SearchCriteria {

	private Long authorId;

	private List<Long> tagsIdList;

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public List<Long> getTagsIdList() {
		return tagsIdList;
	}

	public void setTagsIdList(List<Long> tagsIdList) {
		this.tagsIdList = tagsIdList;
	}

}
