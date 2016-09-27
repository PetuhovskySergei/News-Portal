package com.epam.project.entity;

import java.io.Serializable;

public class Tag implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
		private long tagId;
		private String tagName;
		
		
		public long getTagId() {
			return tagId;
		}
		public void setTagId(long tagId) {
			this.tagId = tagId;
		}
		public String getTagName() {
			return tagName;
		}
		public void setTagName(String tagName) {
			this.tagName = tagName;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (tagId ^ (tagId >>> 32));
			result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
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
			Tag other = (Tag) obj;
			if (tagId != other.tagId)
				return false;
			if (tagName == null) {
				if (other.tagName != null)
					return false;
			} else if (!tagName.equals(other.tagName))
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			return "Tag [tagId=" + tagId + ", tagName=" + tagName + "]";
		}
		
}
