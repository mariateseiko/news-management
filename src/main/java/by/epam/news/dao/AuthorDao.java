package by.epam.news.dao;

import by.epam.news.domain.Author;

import java.util.List;

/**
 * Represents an interface for retrieving author-related data
 */
public interface AuthorDao extends GenericDao<Long, Author> {
    /**
     * Retrieves a list of a news message authors
     * @param newsId id of the news message
     * @return list of authors
     * @throws DaoException if exception occurred on the current level
     */
    List<Author> selectForNews(Long newsId) throws DaoException;

    /**
     * Retrieves a list of all non-expired authors
     * @return a list of authors
     * @throws DaoException if exception occurred on the current level
     */
    List<Author> selectNotExpired() throws DaoException;

    /**
     * Updates an author's expired field to the current timestamp
     * @param authorId id fo the author to mar expired
     * @return true if update succeeded
     * @throws DaoException if exception occurred on the current level
     */
    boolean updateExpired(Long authorId) throws DaoException;
}
