package com.epam.project.util;

import com.epam.project.service.exception.ServiceException;

public class Pagination {
	private static final int COUNT_NEWS_ON_PAGE = 3;

	public static Long getCountPages(Long count) throws ServiceException {

		Long pagesCount = null;

		if (count % COUNT_NEWS_ON_PAGE == 0) {
			pagesCount = count / COUNT_NEWS_ON_PAGE;
		} else {
			pagesCount = (count / COUNT_NEWS_ON_PAGE) + 1;
		}
		return pagesCount;
	}

	public static Integer getStartIndex(Integer numPage) {

		Integer startIdx = COUNT_NEWS_ON_PAGE * numPage - COUNT_NEWS_ON_PAGE;
		return startIdx;
	}

	public static Integer getEndIndex(Integer numPage) {

		Integer endIdx = getStartIndex(numPage) + COUNT_NEWS_ON_PAGE;
		return endIdx;
	}
}
