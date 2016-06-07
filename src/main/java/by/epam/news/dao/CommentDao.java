package by.epam.news.dao;

import by.epam.news.domain.Comment;

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
     * @return true if successfully deleted
     * @throws DaoException if exception occurred on the current level
     */
    boolean delete(Long id) throws DaoException;

    /**
     * Deletes all comments for a specified news message
     * @param newsId id of the news message
     * @return true if successfully deleted
     * @throws DaoException if exception occurred on the current level
     */
    boolean deleteForNews(Long newsId) throws DaoException;
}
