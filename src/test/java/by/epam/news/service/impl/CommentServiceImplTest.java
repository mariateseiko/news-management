package by.epam.news.service.impl;

import by.epam.news.dao.CommentDao;
import by.epam.news.dao.DaoException;
import by.epam.news.domain.Comment;
import by.epam.news.service.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Date;

public class CommentServiceImplTest {
    @Mock
    private CommentDao commentDao;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Long commentId = 1L;
    private String commentText = "Some comment";
    private Long newsId = 1L;
    private Timestamp creationDate = new Timestamp(new Date().getTime());

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSelectById() throws ServiceException, DaoException {
        Comment comment = new Comment(commentId, commentText, creationDate, newsId);
        Mockito.when(commentDao.selectById(commentId)).thenReturn(comment);
        Assert.assertEquals(comment, commentService.findById(commentId));
        Mockito.verify(commentDao).selectById(commentId);
    }

    @Test
    public void addComment() throws ServiceException, DaoException {
        Comment comment = new Comment(commentText, newsId);
        Mockito.when(commentDao.insert(comment)).thenReturn(commentId);
        Assert.assertEquals(commentId, commentService.addComment(comment));
        Mockito.verify(commentDao).insert(comment);
    }

    @Test
    public void deleteComment() throws ServiceException, DaoException {
        Mockito.when(commentDao.delete(commentId)).thenReturn(true);
        Assert.assertTrue(commentService.deleteComment(commentId));
        Mockito.verify(commentDao).delete(commentId);
    }
}
