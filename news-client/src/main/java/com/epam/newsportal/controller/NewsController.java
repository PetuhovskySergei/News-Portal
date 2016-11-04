package com.epam.newsportal.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.epam.project.dao.exception.DAOException;
import com.epam.project.dao.impl.AuthorDAO;
import com.epam.project.dao.impl.CommentDAO;
import com.epam.project.dao.impl.NewsDAO;
import com.epam.project.dao.impl.TagDAO;
import com.epam.project.entity.Comment;
import com.epam.project.entity.NewsFull;
import com.epam.project.service.exception.ServiceException;
import com.epam.project.service.impl.NewsManageService;
import com.epam.project.util.Pagination;
import com.epam.project.util.SearchCriteria;



@Controller
@SessionAttributes({ "criteria" })
public class NewsController {
	
	@Autowired
	private NewsDAO newsService;

	
	@Autowired
	private AuthorDAO authorService;

	
	@Autowired
	private TagDAO tagService;

	
	@Autowired
	private NewsManageService newsManageService;

	
	@Autowired
	private CommentDAO commentService;

	/**
	 * Create SearchCriteria instance before each handling methods.
	 * 
	 * @return search criteria
	 */
	@ModelAttribute("criteria")
	public SearchCriteria getCriteria() {
		return new SearchCriteria();
	} 

	/**
	 * Handles requests for reset search criteria and. Fills data model. And redirect on the news URL.
	 * 
	 * @param numPage
	 *            number of the page
	 * @param model
	 *            data model
	 * @return path of the redirection
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/newslist")
	public String resetCriteria(@RequestParam(value = "numPage", defaultValue = "1", required = false) Integer numPage,
			Model model) throws ServiceException {

		model.addAttribute("criteria", new SearchCriteria());

		return "redirect:/";
	} 

	/**
	 * Fills model and send response to client.
	 * 
	 * @param searchCriteria
	 *            search criteria
	 * @param numPage
	 *            number of the page
	 * @param model
	 *            data model
	 * @return view home
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String adminPage(@ModelAttribute("criteria") SearchCriteria searchCriteria,
			@RequestParam(value = "numPage", defaultValue = "1", required = false) Integer numPage, Model model)
			throws ServiceException {
	//public String adminPage(Model model)
		//throws ServiceException {
		List<NewsFull> newsFullList = newsManageService.searchNews(searchCriteria, numPage);
	
		try {
			model.addAttribute("authorsList", authorService.list());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			model.addAttribute("tagsList", tagService.list());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("newsList", newsFullList);
		
		Long count = null;
		try {
			count = newsService.getCountNews(searchCriteria);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("pagesCount", Pagination.getCountPages(count));
		return "index";
	}
	
	
	
	
	@RequestMapping(value = "/viewNews/{newsId}", method = RequestMethod.GET)
	public String viewNews(@ModelAttribute("criteria") SearchCriteria searchCriteria,
			@PathVariable("newsId") Long newsId, Model model) throws ServiceException {

		model.addAttribute("newsFull", newsManageService.getSingleNewsFull(newsId));
		try {
			model.addAttribute("previousNewsId", newsService.getPreviousNews(searchCriteria, newsId));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			model.addAttribute("nextNewsId", newsService.getNextNews(searchCriteria, newsId));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "viewNews";
	}

	/**
	 * Handles requests for addition new comment. And redirect on the viewNews URL.
	 * 
	 * @param commentText
	 *            comment text
	 * @param newsId
	 *            news ID
	 * @return path of the redirection
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{newsId}/addComment", method = RequestMethod.POST)
	public String addComment(@RequestParam("commentText") String commentText, @RequestParam("newsId") Long newsId)
			throws ServiceException {

		Comment comment = new Comment();
		comment.setNewsId(newsId);
		comment.setCommentText(commentText);
		comment.setCommentDate(null);

		try {
			commentService.insert(comment);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/viewNews/{newsId}";
	}

	/**
	 * Handles requests for delete comment. And redirect on the viewNews URL.
	 * 
	 * @param commentId
	 *            comment ID
	 * @return path of the redirection
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{newsId}/deleteComment/{commentId}", method = RequestMethod.POST)
	public String deleteComment(@PathVariable("commentId") Long commentId) throws ServiceException {

		try {
			commentService.delete(commentId);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/viewNews/{newsId}";
	}
}