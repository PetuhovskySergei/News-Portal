package com.epam.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.project.entity.Tag;


public interface TagRepository extends JpaRepository<Tag, Long> {

}
