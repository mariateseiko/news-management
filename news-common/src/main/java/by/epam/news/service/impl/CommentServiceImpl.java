package by.epam.news.service.impl;

import by.epam.news.dao.CommentDao;
import by.epam.news.dao.DaoException;
import by.epam.news.domain.Comment;
import by.epam.news.service.CommentService;
import by.epam.news.service.ServiceException;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    private CommentDao commentDao;

    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public Long addComment(Comment comment) throws ServiceException {
        try {
            return commentDao.insert(comment);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteComment(Long commentId) throws ServiceException {
        try {
            commentDao.delete(commentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Comment findById(Long commentId) throws ServiceException {
        try {
            return commentDao.selectById(commentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteCommentsForNews(Long newsId) throws ServiceException {
        try {
            commentDao.deleteForNews(newsId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Comment> findCommentForNews(Long newsId) throws ServiceException {
        try {
            return commentDao.selectForNews(newsId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
