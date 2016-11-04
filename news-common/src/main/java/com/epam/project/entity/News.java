package com.epam.project.entity;

import java.io.Serializable;
import java.sql.Date;

public class News implements Serializable {

	private static final long serialVersionUID = 1L;

	private long newsId;
	private String mainTitle;
	private String shortTitle;
	private String text;
	private Date publishDate;

	public long getNewsId() {
		return newsId;
	}

	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}

	public String getMainTitle() {
		return mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((publishDate == null) ? 0 : publishDate.hashCode());
		result = prime * result
				+ ((mainTitle == null) ? 0 : mainTitle.hashCode());
		result = prime * result + (int) (newsId ^ (newsId >>> 32));
		result = prime * result
				+ ((shortTitle == null) ? 0 : shortTitle.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		News other = (News) obj;
		if (publishDate == null) {
			if (other.publishDate != null)
				return false;
		} else if (!publishDate.equals(other.publishDate))
			return false;
		if (mainTitle == null) {
			if (other.mainTitle != null)
				return false;
		} else if (!mainTitle.equals(other.mainTitle))
			return false;
		if (newsId != other.newsId)
			return false;
		if (shortTitle == null) {
			if (other.shortTitle != null)
				return false;
		} else if (!shortTitle.equals(other.shortTitle))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "News [newsId=" + newsId + ", mainTitle=" + mainTitle
				+ ", shortTitle=" + shortTitle + ", text=" + text
				+ ", publishDate=" + publishDate + "]";
	}

}
