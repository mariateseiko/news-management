package by.epam.news.service;

import by.epam.news.domain.Tag;

import java.util.List;

/**
 * Represents a service providing common tag-related actions
 */
public interface TagService {
    /**
     * Adds a given tag to the system
     * @param tag tag to be added with a specified name
     * @return unique generated id of the added tag, or null if such tag already exists
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Long addTag(Tag tag) throws ServiceException;

    /**
     * Retrieves a tag with a given id
     * @param tagId id of the tag to retrieve
     * @return tag entity corresponding to a given id, or null if such doesn't exist
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Tag findTagById(Long tagId) throws ServiceException;

    /**
     * Retrieves a list of all existing tags
     * @return a list of all tags
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    List<Tag> findAll() throws ServiceException;

    /**
     * Updates an existing tag
     * @param tag tag with an id and updated fields
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Boolean updateTag(Tag tag) throws ServiceException;

    /**
     * Deletes a tag with a specified id
     * @param tagId id of the tag to delete
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    void deleteTag(Long tagId) throws ServiceException;

    /**
     * Returns a list of all tags for a given news message
     * @param newsId id of the news message
     * @return list of tags
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    List<Tag> findNewsTags(Long newsId) throws ServiceException;

    /**
     * Unlinks all tags from a given news message
     * @param newsId id of the news message
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    void unlinkAllTagsFromNews(Long newsId) throws ServiceException;

    /**
     * Links a tags to a given news message
     * @param newsId id of the news message
     * @param tagId id of the tag
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    void linkTagToNews(Long newsId, Long tagId) throws ServiceException;
}
