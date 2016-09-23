package com.epam.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.project.entity.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {

}
