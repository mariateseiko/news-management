package by.epam.news.service;

import by.epam.news.domain.Author;

import java.util.List;

/**
 * Represents a service for common author-related actions
 */
public interface AuthorService {
    /**
     * Adds a new author to the system
     * @param author author with a specified name to be added
     * @return generated id of the added author or null, if insert failed
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Long addAuthor(Author author) throws ServiceException;

    /**
     * Marks an author with a given id as expired
      * @param authorId id of the author to mark expired
     * @return true if action succeeded
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    boolean markExpired(Long authorId) throws ServiceException;

    /**
     * Retrieves an author by a given id
     * @param authorId id of the author to retrieve
     * @return retrieved entity
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Author findAuthorById(Long authorId) throws ServiceException;

    /**
     * Retrieves a list of all not expired authors
     * @return list of not expired authors or null, if such don't exist
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    List<Author> findNotExpiredAuthors() throws ServiceException;

    /**
     * Updates an existing author
     * @param author author entity with updated fields and id
     * @return true if update succeeded
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    boolean updateAuthor(Author author) throws ServiceException;
}
