package com.epam.project.util;

import java.util.List;

public class QueryBuilder {
	private final static String SEARCH_NEWS_SQL = "WITH ALL_NEWS AS("
			+ "SELECT NEWS.NEWS_ID, NEWS.SHORT_TITLE, NEWS.TEXT, NEWS.MAIN_TITLE, NEWS.PUBLISH_DATE "
			+ "FROM NEWS_PORTAL.NEWS LEFT JOIN COMMENTS ON NEWS.NEWS_ID = COMMENTS.NEWS_ID";

	/**
	 * Builds SQL query for search operation.
	 * 
	 * @param searchCriteria
	 *            search criteria.
	 * @return query
	 */
	public static String buildSearchQuery(SearchCriteria searchCriteria) {

		StringBuilder fullQuery = buildQueryStructure(searchCriteria);

		fullQuery
				.append(" GROUP BY (NEWS.NEWS_ID, NEWS.SHORT_TITLE, NEWS.TEXT, NEWS.MAIN_TITLE, NEWS.PUBLISH_DATE) ORDER BY COUNT(COMMENTS.COMMENT_ID) DESC, NEWS.PUBLISH_DATE DESC, NEWS.NEWS_ID NULLS LAST), NEWS_ROW as (select an.*, ROWNUM rn from ALL_NEWS an) SELECT nr.NEWS_ID, nr.SHORT_TITLE, nr.TEXT, nr.MAIN_TITLE, nr.PUBLISH_DATE FROM NEWS_ROW nr WHERE rn > ? AND rn <= ?");

		return fullQuery.toString();
	}

	/**
	 * Builds SQL query for counting the number of news.
	 * 
	 * @param searchCriteria
	 *            search news.
	 * @return query
	 */
	public static String buildCountQuery(SearchCriteria searchCriteria) {

		StringBuilder fullQuery = buildQueryStructure(searchCriteria);

		fullQuery
				.append(" GROUP BY (NEWS.NEWS_ID, NEWS.SHORT_TITLE, NEWS.TEXT, NEWS.MAIN_TITLE, NEWS.PUBLISH_DATE) ORDER BY COUNT(COMMENTS.COMMENT_ID) DESC, NEWS.NEWS_ID NULLS LAST) SELECT COUNT(*) FROM ALL_NEWS");

		return fullQuery.toString();
	}

	/**
	 * Builds SQL query for getting the news ID of the next news.
	 * 
	 * @param searchCriteria
	 *            search news.
	 * @return query
	 */
	public static String buildNextQuery(SearchCriteria searchCriteria) {

		StringBuilder fullQuery = buildQueryStructure(searchCriteria);

		fullQuery
				.append(" GROUP BY (NEWS.NEWS_ID, NEWS.SHORT_TITLE, NEWS.TEXT, NEWS.MAIN_TITLE, NEWS.PUBLISH_DATE) ORDER BY COUNT(COMMENTS.COMMENT_ID) DESC, NEWS.NEWS_ID NULLS LAST), NEWS_ROW as (select an.*, ROWNUM rn from ALL_NEWS an) SELECT nr.NEWS_ID FROM NEWS_ROW nr, (SELECT MIN(rn) as idx FROM NEWS_ROW WHERE news_id=?)NI WHERE rn = idx + 1");

		return fullQuery.toString();
	}

	/**
	 * Builds SQL query for getting the news ID of the previous news.
	 * 
	 * @param searchCriteria
	 *            search criteria.
	 * @return query
	 */
	public static String buildPreviousQuery(SearchCriteria searchCriteria) {

		StringBuilder fullQuery = buildQueryStructure(searchCriteria);

		fullQuery
				.append(" GROUP BY (NEWS.NEWS_ID, NEWS.SHORT_TITLE, NEWS.TEXT, NEWS.MAIN_TITLE, NEWS.PUBLISH_DATE) ORDER BY COUNT(COMMENTS.COMMENT_ID) DESC, NEWS.NEWS_ID NULLS LAST), NEWS_ROW as (select an.*, ROWNUM rn from ALL_NEWS an) SELECT nr.NEWS_ID FROM NEWS_ROW nr, (SELECT MIN(rn) as idx FROM NEWS_ROW WHERE news_id=?)NI WHERE rn = idx - 1");

		return fullQuery.toString();
	}

	/**
	 * Builds main part of the query.
	 * 
	 * @param searchCriteria
	 *            search criteria.
	 * @return
	 */
	private static StringBuilder buildQueryStructure(
			SearchCriteria searchCriteria) {

		StringBuilder searchQuery = new StringBuilder(SEARCH_NEWS_SQL);
		List<Long> tagsIdList = searchCriteria.getTagsIdList();
		Long authorId = searchCriteria.getAuthorId();

		if ((authorId != null && authorId != 0) || tagsIdList != null) {
			StringBuilder whereQuery = new StringBuilder(" WHERE 1=2");
			StringBuilder joinQuery = new StringBuilder();

			if (authorId != null && authorId != 0) {
				whereQuery.append(" OR news_author.author_id=?");
				joinQuery
						.append(" JOIN news_author ON news.news_id=news_author.news_id");
			}

			if (tagsIdList != null && !tagsIdList.isEmpty()) {
				whereQuery.append(" OR news_tag.tag_id IN(");
				for (int i = 1; i < tagsIdList.size(); i++) {
					whereQuery.append("?, ");
				}

				whereQuery.append("?)");
				joinQuery
						.append(" LEFT JOIN news_tag ON news.news_id=news_tag.news_id");
			}
			searchQuery.append(joinQuery).append(whereQuery);
		}

		return searchQuery;
	}
}