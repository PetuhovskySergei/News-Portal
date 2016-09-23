package com.epam.project.service.impl;

import java.util.List;

import com.epam.project.entity.Comment;
import com.epam.project.repository.CommentRepository;
import com.epam.project.service.CommentService;

public class CommentServiceImpl implements CommentService {
	
	private CommentRepository commentRepository;

	@Override
	public Comment insertComment(Comment comment) {
		
		Comment savedComment = commentRepository.saveAndFlush(comment);
		 
        return savedComment;
	}

	@Override
	public void deleteComment(long commentId) {
		
		commentRepository.delete(commentId);		
	}

	@Override
	public Comment showComment(long commentId) {
		
		return commentRepository.findOne(commentId);
	}

	@Override
	public void updateComment(Comment comment) {
		
		  commentRepository.saveAndFlush(comment);
		
	}

	@Override
	public List<Comment> listComment() {
		return commentRepository.findAll();
	}
}
