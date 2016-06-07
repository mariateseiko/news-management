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
    List<News> selectNewsByAuthor(Long authorId);

    /**
     * Links a tag to a news message
     * @param newsId id of the news
     * @param tagId id of the tag
     * @return true if successfully linked
     * @throws DaoException if exception occurred on the current level
     */
    boolean linkTagNews(Long newsId, Long tagId) throws DaoException;

    /**
     * Unlinks a tag from a news messages
     * @param newsId id of the news
     * @param tagId id of the tag
     * @return true if successfully unlinked
     * @throws DaoException if exception occurred on the current level
     */
    boolean unlinkTagNews(Long newsId, Long tagId) throws DaoException;

    /**
     * Links an author to a news message
     * @param newsId id of the news message
     * @param authorId id of the author
     * @return true is successfully linked
     * @throws DaoException if exception occurred on the current level
     */
    boolean linkAuthorNews(Long newsId, Long authorId) throws DaoException;

    /**
     * Deletes a specified news message
     * @param newsId id of the news message to delete
     * @return true if successfully deleted
     * @throws DaoException if exception occurred on the current level
     */
    boolean delete(Long newsId) throws DaoException;

    /**
     * Retrieves a list of all news sorted by comments count
     * @return a list of all news
     * @throws DaoException if exception occurred on the current level
     */
    List<News> selectAllNews() throws DaoException;

    /**
     * Retrieves a list of news matching the search criteria
     * @param searchCriteria search criteria with authors and tags
     * @return a list of news corresponding to the criteria
     * @throws DaoException if exception occurred on the current level
     */
    List<News> selectBySearchCriteria(SearchCriteria searchCriteria) throws DaoException;
}
