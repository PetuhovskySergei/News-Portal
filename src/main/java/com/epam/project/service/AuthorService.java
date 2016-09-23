package com.epam.project.service;

import com.epam.project.entity.Author;

public interface AuthorService {
	Author insertAuthor(Author author);
	void deleteAuthor(long authorId);
	Author showAuthor(long authorId);
	void updateAuthor(Author author);
}
