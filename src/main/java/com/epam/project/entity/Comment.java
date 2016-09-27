package com.epam.project.entity;

import java.io.Serializable;
import java.sql.Date;

public class Comment implements Serializable {
	
	private static final long serialVersionUID = 1L;

		private long commentId;
		private String commentText;
		private Date commentDate;
		
		
		public long getCommentId() {
			return commentId;
		}
		public void setCommentId(long commentId) {
			this.commentId = commentId;
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
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((commentDate == null) ? 0 : commentDate.hashCode());
			result = prime * result + (int) (commentId ^ (commentId >>> 32));
			result = prime * result + ((commentText == null) ? 0 : commentText.hashCode());
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
			Comment other = (Comment) obj;
			if (commentDate == null) {
				if (other.commentDate != null)
					return false;
			} else if (!commentDate.equals(other.commentDate))
				return false;
			if (commentId != other.commentId)
				return false;
			if (commentText == null) {
				if (other.commentText != null)
					return false;
			} else if (!commentText.equals(other.commentText))
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			return "Comment [commentId=" + commentId + ", commentText=" + commentText
					+ ", commentDate=" + commentDate + "]";
		}
		
}
