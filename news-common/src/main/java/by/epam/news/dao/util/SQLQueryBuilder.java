package by.epam.news.dao.util;

import by.epam.news.domain.SearchCriteria;

import java.util.List;

/**
 * Represents a class for building sql queries for searching news according to a search criteria
 */
public class SQLQueryBuilder {
    private static final String SEARCH_QUERY = "SELECT news.news_id, news.title, news.short_text, " +
            "news.full_text, news.creation_date, " +
            "modification_date FROM news " +
            "JOIN news_authors ON news.news_id = news_authors.news_id " +
            "LEFT JOIN comments ON news.news_id = comments.news_id " +
            "LEFT JOIN news_tags ON news.news_id = news_tags.news_id " ;
    private static final String GROUP_BY_CLAUSE = "GROUP BY news.news_id, news.title, " +
            "news.short_text, news.full_text, " +
            "news.creation_date, news.modification_date ";
    private static final String ORDER_BY_CLAUSE = "ORDER BY COUNT(comment_id)";

    private static final String WHERE_TAG_CLAUSE = "WHERE news_tags.tag_id IN (";
    private static final String WHERE_AUTHOR_CLAUSE = "news_authors.author_id IN (";
    private static final String AND_CLAUSE = " AND ";
    private static final String COMMA = ", ";
    private static final String CLOSING_BRACKET = ") ";

    public static String buildSearchQuery(SearchCriteria searchCriteria) {
        List<Long> authorsId = searchCriteria.getAuthorsId();
        List<Long> tagsId = searchCriteria.getTagsId();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(SEARCH_QUERY);
        queryBuilder.append(WHERE_TAG_CLAUSE);
        for (Long tagId: tagsId) {
            queryBuilder.append(tagId);
            queryBuilder.append(COMMA);
        }
        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
        queryBuilder.append(CLOSING_BRACKET);
        queryBuilder.append(AND_CLAUSE);
        queryBuilder.append(WHERE_AUTHOR_CLAUSE);
        for (Long authorId: authorsId) {
            queryBuilder.append(authorId);
            queryBuilder.append(COMMA);
        }
        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
        queryBuilder.append(CLOSING_BRACKET);
        queryBuilder.append(GROUP_BY_CLAUSE);
        queryBuilder.append(ORDER_BY_CLAUSE);
        return queryBuilder.toString();
    }


}
