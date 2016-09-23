package com.epam.project.service;

import java.util.List;

import com.epam.project.entity.Comment;

public interface CommentService {
	List<Comment> listComment();
	Comment insertComment(Comment comment);
	void deleteComment(long commentId);
	Comment showComment(long commentId);
	void updateComment(Comment comment);
}
