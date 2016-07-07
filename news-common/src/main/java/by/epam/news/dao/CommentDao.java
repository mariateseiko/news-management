package by.epam.news.dao;

import by.epam.news.domain.Comment;
import by.epam.news.service.ServiceException;

import java.util.List;

/**
 * Represents an interface for retrieving comment-related data
 */
public interface CommentDao extends GenericDao<Long, Comment> {
    /**
     * Retrieves a list of comments for a specified news message
     * @param newsId id of the news message
     * @return a list of comments for the news
     * @throws DaoException if exception occurred on the current level
     */
    List<Comment> selectForNews(Long newsId) throws DaoException;

    /**
     * Deletes a specified comment
     * @param id id fo the comment to delete
     * @throws DaoException if exception occurred on the current level
     */
    void delete(Long id) throws DaoException;

    /**
     * Deletes all comments for a specified news message
     * @param newsId id of the news message
     * @throws DaoException if exception occurred on the current level
     */
    void deleteForNews(Long newsId) throws DaoException;

    /**
     * Defines total number of comments for a given news
     * @param newsId id of the news message
     * @return total count of comments
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Integer selectNewsCommentsCount(Long newsId) throws DaoException;
}
