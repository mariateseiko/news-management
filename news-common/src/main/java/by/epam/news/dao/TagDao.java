package by.epam.news.dao;

import by.epam.news.domain.Tag;

import java.util.List;

/**
 * Represents an interface for retrieving tag-related dao
 */
public interface TagDao extends GenericDao<Long, Tag> {
    /**
     * Retrieves all existing tags
     * @return a list of all existing tags
     * @throws DaoException if exception occurred on the current level
     */
    List<Tag> selectAll() throws DaoException;

    /**
     * Retrieves a list of all tags for a specified news message
     * @param newsId id of the message to retrieve tags for
     * @return a list of tags for the news message
     * @throws DaoException if exception occurred on the current level
     */
    List<Tag> selectForNews(Long newsId) throws DaoException;

    /**
     * Deletes a tag with a specified id
     * @param tagId id of the tag to delete
     * @throws DaoException if exception occurred on the current level
     */
    void delete(Long tagId) throws DaoException;

    /**
     * Links a tag to a news message
     * @param newsId id of the news
     * @param tagId id of the tag
     * @return true if successfully linked
     * @throws DaoException if exception occurred on the current level
     */
    void linkTagNews(Long newsId, Long tagId) throws DaoException;

    /**
     * Unlinks all tags from a news messages
     * @param newsId id of the news
     * @throws DaoException if exception occurred on the current level
     */
    void unlinkAllTags(Long newsId) throws DaoException;
}
