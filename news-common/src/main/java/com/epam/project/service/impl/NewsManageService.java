package com.epam.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.project.entity.Author;
import com.epam.project.entity.Comment;
import com.epam.project.entity.News;
import com.epam.project.entity.NewsFull;
import com.epam.project.entity.Tag;
import com.epam.project.service.INewsManageService;
import com.epam.project.service.exception.ServiceException;
import com.epam.project.util.SearchCriteria;

@Service
public class NewsManageService implements INewsManageService {

	@Autowired
	private AuthorService authorService;

	@Autowired
	private NewsService newsService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private TagService tagService;

	@Override
	public Long saveNews(NewsFull newsFull) throws ServiceException {

		News news = newsFull.getNews();
		Long newsId = newsService.insert(news);
		Long authorId = newsFull.getAuthor().getAuthorId();
		List<Tag> tagsList = newsFull.getTagsList();
		Long tagId = null;

		newsService.addAuthor(newsId, authorId);

		if (tagsList != null) {
			for (Tag tag : tagsList) {
				tagId = tag.getTagId();
				newsService.addTag(newsId, tagId);
			}
		}

		return newsId;
	}

	@Override
	public void deleteNews(List<Long> newsListId) throws ServiceException {

		for (Long newsId : newsListId) {
			newsService.deleteAuthorNews(newsId);
			newsService.deleteTagNews(newsId);
			newsService.delete(newsId);
		}
	}

	@Override
	public void updateNews(NewsFull newsFull) throws ServiceException {

		News news = newsFull.getNews();
		Long newsId = news.getNewsId();
		Long authorId = newsFull.getAuthor().getAuthorId();
		List<Tag> tagsList = newsFull.getTagsList();
		Long tagId = null;

		newsService.update(news);
		newsService.updateAuthorNews(newsId, authorId);
		newsService.deleteTagNews(newsId);

		if (tagsList != null) {
			for (Tag tag : tagsList) {
				tagId = tag.getTagId();
				newsService.addTag(newsId, tagId);
			}
		}
	}

	@Override
	public NewsFull getSingleNewsFull(Long newsId) throws ServiceException {
		News news = null;
		Author author = null;
		List<Tag> tagsList = null;
		Long countComment = null;
		List<Comment> commentsList = null;
		NewsFull newsFull = null;

		news = newsService.show(newsId);

		newsFull = new NewsFull();
		author = newsService.getAuthorNews(newsId);
		tagsList = newsService.getTagsNews(newsId);
		commentsList = newsService.getCommentsNews(newsId);
		countComment = (long) commentsList.size();
		newsFull.setNews(news);
		newsFull.setAuthor(author);
		newsFull.setCommentsList(commentsList);
		newsFull.setCountComment(countComment);
		newsFull.setTagsList(tagsList);

		return newsFull;
	}

	@Override
	public List<NewsFull> searchNews(SearchCriteria searchCriteria,
			Integer numPage) throws ServiceException {
		List<NewsFull> newsFullList = new ArrayList<>();
		List<News> newsList = null;
		Long newsId = null;
		NewsFull newsFull = null;

		newsList = newsService.searchNews(searchCriteria, numPage);

		if (newsList.isEmpty()) {
			throw new ServiceException("News list is empty.");
		}

		for (News news : newsList) {
			newsId = news.getNewsId();
			newsFull = getSingleNewsFull(newsId);
			newsFullList.add(newsFull);
		}
		return newsFullList;

	}

}
