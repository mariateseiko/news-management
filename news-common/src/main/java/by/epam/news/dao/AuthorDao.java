package by.epam.news.dao;

import by.epam.news.domain.Author;
import by.epam.news.domain.User;

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
     * @throws DaoException if exception occurred on the current level
     */
    void updateExpired(Long authorId) throws DaoException;

    /**
     * Unlinks all authors from a news messages
     * @param newsId id of the news
     * @throws DaoException if exception occurred on the current level
     */
    void unlinkAllAuthors(Long newsId) throws DaoException;


    /**
     * Links an author to a news message
     * @param newsId id of the news message
     * @param authorId id of the author
     * @return true is successfully linked
     * @throws DaoException if exception occurred on the current level
     */
    void linkAuthorNews(Long newsId, Long authorId) throws DaoException;

    /**
     * Updates a given entity
     * @param author entity to update
     * @throws DaoException if exception occurred on current level
     */
    void update(Author author) throws DaoException;
}
