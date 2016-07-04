package by.epam.news.service;

import by.epam.news.domain.Comment;

import java.util.List;

/**
 * Represents a service providing common comment-related actions
 */
public interface CommentService {
    /**
     * Adds a new comment to an existing news message
     * @param comment comment to add
     * @return generated id of the comment
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Long addComment(Comment comment) throws ServiceException;

    /**
     * Deletes a comment with a given id
     * @param commentId id of the comment to delete
     * @return true if deletion succeeded
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    void deleteComment(Long commentId) throws ServiceException;

    /**
     * Retrieves a comment by its id
     * @param commentId id of the comment to retrieve
     * @return retrieved comment
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    Comment findById(Long commentId) throws ServiceException;

    /**
     * Deletes all comments for a given news message
     * @param newsId id of the news message
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    void deleteCommentsForNews(Long newsId) throws ServiceException;

    /**
     * Finds all comments for a given news message
     * @param newsId id of the news message
     * @return list of comments
     * @throws ServiceException if exception occurred on the service or any underlying level
     */
    List<Comment> findCommentForNews(Long newsId) throws ServiceException;
}
