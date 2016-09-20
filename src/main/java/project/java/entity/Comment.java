package project.java.entity;

import java.sql.Date;

public class Comment {

		private int commentId;
		private int userId;
		private int newsId;
		private String commentText;
		private Date commentDate;
		
		
		public int getCommentId() {
			return commentId;
		}
		public void setCommentId(int commentId) {
			this.commentId = commentId;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public int getNewsId() {
			return newsId;
		}
		public void setNewsId(int newsId) {
			this.newsId = newsId;
		}
		public String getCommentText() {
			return commentText;
		}
		public void setCommentText(String commentText) {
			this.commentText = commentText;
		}
		public Date getCommentDate() {
			return commentDate;
		}
		public void setCommentDate(Date commentDate) {
			this.commentDate = commentDate;
		}
		
}
