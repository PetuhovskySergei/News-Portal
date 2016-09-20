package project.java.entity;

import java.sql.Date;

public class News {
		
		private int newsId;
		private String mainTitle;
		private String shortTitle;
		private String text;
		private int authorId;
		private Date dateOfPublishing; 
		private int tagId;
		private int commentId;
		//maybe something about "main photo"
		
		public int getNewsId() {
			return newsId;
		}
		public void setNewsId(int newsId) {
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
		public int getAuthorId() {
			return authorId;
		}
		public void setAuthorId(int authorId) {
			this.authorId = authorId;
		}
		public Date getDateOfPublishing() {
			return dateOfPublishing;
		}
		public void setDateOfPublishing(Date dateOfPublishing) {
			this.dateOfPublishing = dateOfPublishing;
		}
		public int getTagId() {
			return tagId;
		}
		public void setTagId(int tagId) {
			this.tagId = tagId;
		}
		public int getCommentId() {
			return commentId;
		}
		public void setCommentId(int commentId) {
			this.commentId = commentId;
		}
		
}
