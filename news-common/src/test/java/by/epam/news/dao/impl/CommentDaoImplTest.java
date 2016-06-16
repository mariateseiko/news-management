package by.epam.news.dao.impl;

import by.epam.news.dao.CommentDao;
import by.epam.news.dao.DaoException;
import by.epam.news.domain.Comment;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:testApplicationContext.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:xmldata/newsData.xml")
@DatabaseTearDown(value = { "classpath:xmldata/newsData.xml" }, type = DatabaseOperation.DELETE)
public class CommentDaoImplTest {
    @Autowired
    private CommentDao commentDao;

    @Test
    public void testSelectById() throws DaoException {
        Long commentId = 1L;
        Comment comment = commentDao.selectById(commentId);
        Assert.assertEquals(commentId, comment.getId());
    }

    @Test
    @ExpectedDatabase(value = "classpath:xmldata/commentDataUpdate.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = { "classpath:xmldata/commentDataUpdate.xml" }, type = DatabaseOperation.DELETE)
    public void testUpdate() throws DaoException {
        Comment comment = new Comment();
        Long commentId = 1L;
        comment.setId(commentId);
        comment.setCommentText("Some new test comment");
        Assert.assertTrue(commentDao.update(comment));
    }

    @Test
    public void testSelectAllByNewsId() throws DaoException {
        Long newsId = 1L;
        List<Comment> comments = commentDao.selectForNews(newsId);
        Assert.assertEquals(2, comments.size());
    }

    @Test
    public void testInsert() throws DaoException {
        Long newsId = 3L;
        Comment comment = new Comment();
        comment.setCommentText("Rather important opinion");
        comment.setNewsId(newsId);
        Long id = commentDao.insert(comment);
        Assert.assertTrue(id > 0);
        Comment insertedComment = commentDao.selectById(id);
        Assert.assertEquals(comment.getCommentText(), insertedComment.getCommentText());
    }

    @Test
    @ExpectedDatabase(value = "classpath:xmldata/commentDataDelete.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = { "classpath:xmldata/commentDataDelete.xml" }, type = DatabaseOperation.DELETE)
    public void testDelete() throws DaoException {
        Long commentId = 1L;
        Assert.assertTrue(commentDao.delete(commentId));
    }

    @Test
    @ExpectedDatabase(value = "classpath:xmldata/commentDataDeleteByNews.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = { "classpath:xmldata/commentDataDeleteByNews.xml" }, type = DatabaseOperation.DELETE)
    public void testDeleteByNewsId() throws DaoException {
        Long newsId = 1L;
        Assert.assertTrue(commentDao.deleteForNews(newsId));
    }
}
