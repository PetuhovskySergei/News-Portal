package com.epam.project.service.impl;

import com.epam.project.entity.Author;
import com.epam.project.repository.AuthorRepository;
import com.epam.project.service.AuthorService;

public class AuthorServiceImpl implements AuthorService {
	
	private AuthorRepository authorRepository;

	@Override
	public Author insertAuthor(Author author) {
		
		Author savedAuthor = authorRepository.saveAndFlush(author);
		 
        return savedAuthor;
	}

	@Override
	public void deleteAuthor(long authorId) {
		
		authorRepository.delete(authorId);		
	}

	@Override
	public Author showAuthor(long authorId) {
		
		return authorRepository.findOne(authorId);
	}

	@Override
	public void updateAuthor(Author author) {
		
		  authorRepository.saveAndFlush(author);
		
	}
}
