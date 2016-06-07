package by.epam.news.service;

import by.epam.news.domain.News;
import by.epam.news.domain.NewsDTO;
import by.epam.news.domain.SearchCriteria;

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
    Integer countNews() throws ServiceException;

    /**
     * Adds a news message with tags and author to the system
     * @param newsDTO transfer object containing a news message, author and tags
     * @return generated id of the inserted news
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Long addNews(NewsDTO newsDTO) throws ServiceException;

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
    List<NewsDTO> findAllNews() throws ServiceException;

    /**
     * Retrieves a list of all news messages by a given author
     * with tags and authors, sorted by comments count
     * @return list of all author's news
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    List<NewsDTO> findNewsByAuthor(Long authorId) throws ServiceException;

    /**
     * Deletes a news message with its comments
     * @param newsId id of the news message to delete
     * @return true if successfully deleted
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    boolean deleteNews(Long newsId) throws ServiceException;

    /**
     * Adds new tags to the news message
     * @param newsId id of the news message to edit
     * @param tagsId tags list of tags ids to add
     * @return true id successfully added
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    boolean addTagsToNews(Long newsId, List<Long> tagsId) throws ServiceException;

    /**
     * Removes a tag from a news message
     * @param newsId id of the news to delete tag from
     * @param tagId id of the tag to delete
     * @return if successfully deleted
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    boolean deleteTagFromNews(Long newsId, Long tagId) throws ServiceException;

    /**
     * Updates a news message
     * @param news updated news entity with an id
     * @return true if successfully updated
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    boolean updateNews(News news) throws ServiceException;

    /**
     * Retrieves a list of transfer objects containing news messages with authors, tags, comments
     * that match the search criteria
     * @param searchCriteria search criteria with tags and authors
     * @return a list of matching news
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    List<NewsDTO> findBySearchCriteria(SearchCriteria searchCriteria) throws ServiceException;
}
