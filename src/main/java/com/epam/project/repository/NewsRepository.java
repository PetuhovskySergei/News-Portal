package com.epam.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.project.entity.News;


public interface NewsRepository extends JpaRepository<News, Long> {

}
