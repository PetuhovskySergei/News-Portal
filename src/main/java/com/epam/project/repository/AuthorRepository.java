package com.epam.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.project.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
