package by.epam.news.service;

import by.epam.news.domain.News;
import by.epam.news.domain.NewsDTO;
import by.epam.news.domain.SearchCriteria;
import org.hibernate.service.Service;

import java.util.List;

/**
 * Represents a service for common news-related actions
 */
public interface NewsService {
    /**
     * Counts all news
     * @return total count of news in the system
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Long countNews() throws ServiceException;

    /**
     * Counts news filtered by search criteria
     * @return total count of news in the system
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Long countFilteredNews(SearchCriteria searchCriteria) throws ServiceException;

    /**
     * Deletes a single news message
     * @param newsId id if the news message to delete
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    void deleteNews(Long newsId) throws ServiceException;

    /**
     * Finds a list of news according to search criteria
     * @param searchCriteria search criteria with tags and authors
     * @return list of news corresponding to the search criteria
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    List<News> findNewsBySearchCriteria(SearchCriteria searchCriteria) throws ServiceException;

    /**
     * Finds a news message by a given id
     * @param newsId id of the news message
     * @return a news object
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    News findNewsById(Long newsId) throws ServiceException;

    /**
     * Finds all news sorted by comment count for a given page according to a limit
     * @param page page number
     * @param limit max number of news on the page
     * @return a list of news
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    List<News> findAllNews(Long page, Long limit) throws ServiceException;

    /**
     * Adds a new news message
     * @param news news message to add
     * @return generated id of the inserted news message
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Long addNews(News news) throws ServiceException;

    /**
     * Updates a news entity
     * @param news news entity with a specified id and updated information
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    void updateNews(News news) throws ServiceException;
}
