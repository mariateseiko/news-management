package by.epam.news.service;

import by.epam.news.domain.NewsDTO;
import by.epam.news.domain.SearchCriteria;

import java.util.List;

public interface NewsServiceFacade {
    /**
     * Retrieves a list of transfer objects containing news messages with authors, tags, comments
     * that match the search criteria
     * @param searchCriteria search criteria with tags and authors
     * @return a list of matching news
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    List<NewsDTO> findBySearchCriteria(SearchCriteria searchCriteria) throws ServiceException;

    /**
     * Deletes a news message with its comments
     * @param newsId id of the news message to delete
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    void deleteNews(Long newsId) throws ServiceException;

    /**
     * Updates an existing news objects and related data or adds it if it doesn't yet exist
     * @param newsDTO transfer object to save or update
     * @return generated id or an existing one, if present
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Long saveNews(NewsDTO newsDTO) throws ServiceException;

    /**
     * Retrieves a transfer object with a news message, author, tags and comments by its id
     * @param newsId id of the news message to retrieve
     * @return transfer object containing a news message, author, comments and tags
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    NewsDTO findById(Long newsId) throws ServiceException;

    /**
     * Retrieves a list of all news messages with tags and authors, sorted by comments count
     * @return list of all news
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    List<NewsDTO> findAllNews(Long page, Long limit) throws ServiceException;

    /**
     * Retrieves a list of all news messages by a given author
     * with tags and authors, sorted by comments count
     * @return list of all author's news
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    /*List<NewsDTO> findNewsByAuthor(Long authorId) throws ServiceException;*/
}
