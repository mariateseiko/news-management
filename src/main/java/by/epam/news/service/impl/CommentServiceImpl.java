package by.epam.news.service.impl;

import by.epam.news.dao.CommentDao;
import by.epam.news.dao.DaoException;
import by.epam.news.domain.Comment;
import by.epam.news.service.CommentService;
import by.epam.news.service.ServiceException;

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
    public boolean deleteComment(Long commentId) throws ServiceException {
        try {
            return commentDao.delete(commentId);
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

}
