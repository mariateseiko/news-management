package by.epam.news.dao.impl;

import by.epam.news.dao.AuthorDao;
import by.epam.news.dao.DaoException;
import by.epam.news.domain.Author;
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
@DatabaseSetup("classpath:xmldata/authorData.xml")
@DatabaseTearDown(value = { "classpath:xmldata/authorData.xml" }, type = DatabaseOperation.DELETE)
public class AuthorDaoImplTest {
    @Autowired
    private AuthorDao authorDao;

    @Test
    public void testSelectById() throws DaoException {
        Long authorId = 1L;
        Author author = authorDao.selectById(authorId);
        Assert.assertEquals(authorId, author.getId());
    }

    @Test
    @ExpectedDatabase(value = "classpath:xmldata/authorDataUpdate.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = { "classpath:xmldata/authorDataUpdate.xml" }, type = DatabaseOperation.DELETE)
    public void testUpdate() throws DaoException {
        Author author = new Author();
        Long authorId = 3L;
        String name = "Random guy";
        author.setId(authorId);
        author.setName(name);
        author.setExpired(null);
        Assert.assertTrue(authorDao.update(author));
    }

    @Test
    public void testInsert() throws DaoException {
        String name = "Jane Doe";
        Author author = new Author();
        author.setName(name);
        Long id = authorDao.insert(author);
        Assert.assertTrue(id > 0);
    }

    @Test
    @DatabaseSetup("classpath:xmldata/newsData.xml")
    @DatabaseTearDown(value = { "classpath:xmldata/newsData.xml" }, type = DatabaseOperation.DELETE)
    public void testSelectByNewsId() throws DaoException {
        Long newsId = 2L;
        List<Author> authors = authorDao.selectForNews(newsId);
        Assert.assertEquals(1, authors.size());
    }

    @Test
    public void testSelectNotExpired() throws DaoException {
        List<Author> authors = authorDao.selectNotExpired();
        Assert.assertEquals(2, authors.size());
    }

    @Test
    public void testUpdateExpired() throws DaoException {
        Long authorId = 1L;
        Assert.assertTrue(authorDao.updateExpired(authorId));
        Assert.assertNotNull(authorDao.selectById(authorId).getExpired());
    }
}
