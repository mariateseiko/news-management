package by.epam.news.service.impl;

import by.epam.news.dao.CommentDao;
import by.epam.news.dao.DaoException;
import by.epam.news.domain.Comment;
import by.epam.news.service.CommentService;
import by.epam.news.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    private CommentDao commentDao;
    private static final Logger LOG = LogManager.getLogger(CommentServiceImpl.class);

    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public Long addComment(Comment comment) throws ServiceException {
        try {
            return commentDao.insert(comment);
        } catch (DaoException e) {
            LOG.error("Failed add comment: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteComment(Long commentId) throws ServiceException {
        try {
            commentDao.delete(commentId);
        } catch (DaoException e) {
            LOG.error("Failed delete comment: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Comment findById(Long commentId) throws ServiceException {
        try {
            return commentDao.selectById(commentId);
        } catch (DaoException e) {
            LOG.error("Failed to find comment by id: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteCommentsForNews(Long newsId) throws ServiceException {
        try {
            commentDao.deleteForNews(newsId);
        } catch (DaoException e) {
            LOG.error("Failed to delete all comments from news: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Comment> findCommentForNews(Long newsId) throws ServiceException {
        try {
            return commentDao.selectForNews(newsId);
        } catch (DaoException e) {
            LOG.error("Failed to find comments for news: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer countNewsComments(Long newsId) throws ServiceException {
        try {
            return commentDao.selectNewsCommentsCount(newsId);
        } catch (DaoException e) {
            LOG.error("Failed to count news for comments: ", e);
            throw new ServiceException(e);
        }
    }
}
