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
     * @return true if update succeeded
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    boolean updateTag(Tag tag) throws ServiceException;
}
