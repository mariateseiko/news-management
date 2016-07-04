package by.epam.news.dao;

import by.epam.news.domain.News;
import by.epam.news.domain.SearchCriteria;

import java.util.List;

/**
 * Represents an interface for retrieving news-related data
 */
public interface NewsDao extends GenericDao<Long, News> {
    /**
     * Counts all existing news messages
     * @return total count of news
     * @throws DaoException if exception occurred on the current level
     */
    Integer selectTotalCount() throws DaoException;

    /**
     * Retrieves  a list of news by a given author
     * @param authorId id of the author
     * @return a list of author's news
     */
    List<News> selectNewsByAuthor(Long authorId) throws DaoException;

    /**
     * Deletes a specified news message
     * @param newsId id of the news message to delete
     * @return true if successfully deleted
     * @throws DaoException if exception occurred on the current level
     */
    void delete(Long newsId) throws DaoException;

    /**
     * Retrieves a list of all news sorted by comments count
     * @return a list of all news
     * @throws DaoException if exception occurred on the current level
     */
    List<News> selectAllNews(Long page, Long limit) throws DaoException;

    /**
     * Retrieves a list of news matching the search criteria
     * @param searchCriteria search criteria with authors and tags
     * @return a list of news corresponding to the criteria
     * @throws DaoException if exception occurred on the current level
     */
    List<News> selectBySearchCriteria(SearchCriteria searchCriteria) throws DaoException;
}
