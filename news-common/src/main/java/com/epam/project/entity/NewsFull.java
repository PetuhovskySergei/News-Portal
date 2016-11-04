package com.epam.project.entity;

import java.io.Serializable;
import java.util.List;

public class NewsFull implements Serializable {

	private static final long serialVersionUID = 1L;

	private News news;
	private Author author;
	private List<Tag> tagsList;
	private Long countComment;
	private List<Comment> commentsList;

	public NewsFull() {
	};

	public NewsFull(News news, Author author, List<Tag> tagsList,
			Long countComment, List<Comment> commentsList) {
		this.news = news;
		this.author = author;
		this.tagsList = tagsList;
		this.countComment = countComment;
		this.commentsList = commentsList;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public List<Tag> getTagsList() {
		return tagsList;
	}

	public void setTagsList(List<Tag> tagsList) {
		this.tagsList = tagsList;
	}

	public Long getCountComment() {
		return countComment;
	}

	public void setCountComment(Long countComment) {
		this.countComment = countComment;
	}

	public List<Comment> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<Comment> commentsList) {
		this.commentsList = commentsList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result
				+ ((commentsList == null) ? 0 : commentsList.hashCode());
		result = prime * result
				+ ((countComment == null) ? 0 : countComment.hashCode());
		result = prime * result + ((news == null) ? 0 : news.hashCode());
		result = prime * result
				+ ((tagsList == null) ? 0 : tagsList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsFull other = (NewsFull) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (commentsList == null) {
			if (other.commentsList != null)
				return false;
		} else if (!commentsList.equals(other.commentsList))
			return false;
		if (countComment == null) {
			if (other.countComment != null)
				return false;
		} else if (!countComment.equals(other.countComment))
			return false;
		if (news == null) {
			if (other.news != null)
				return false;
		} else if (!news.equals(other.news))
			return false;
		if (tagsList == null) {
			if (other.tagsList != null)
				return false;
		} else if (!tagsList.equals(other.tagsList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NewsFull [news=" + news + ", author=" + author + ", tagsList="
				+ tagsList + ", countComment=" + countComment
				+ ", commentsList=" + commentsList + "]";
	}

}
