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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void testAddComment() throws ServiceException, DaoException {
        Comment comment = new Comment(newsId, commentText);
        Mockito.when(commentDao.insert(comment)).thenReturn(commentId);
        Assert.assertEquals(commentId, commentService.addComment(comment));
        Mockito.verify(commentDao).insert(comment);
    }

    @Test
    public void testDeleteComment() throws ServiceException, DaoException {
        commentService.deleteComment(commentId);
        Mockito.verify(commentDao).delete(commentId);
    }

    @Test
    public void testFindNewsComments() throws ServiceException, DaoException {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(commentId, commentText));
        Mockito.when(commentDao.selectForNews(commentId)).thenReturn(comments);
        Assert.assertEquals(comments, commentService.findCommentForNews(commentId));
        Mockito.verify(commentDao).selectForNews(commentId);
    }

    @Test
    public void testDeleteNewsComments() throws ServiceException, DaoException {
        commentService.deleteCommentsForNews(commentId);
        Mockito.verify(commentDao).deleteForNews(commentId);
    }
}
