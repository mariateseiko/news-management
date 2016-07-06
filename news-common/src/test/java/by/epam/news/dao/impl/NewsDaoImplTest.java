package by.epam.news.dao.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.NewsDao;
import by.epam.news.domain.News;
import by.epam.news.domain.SearchCriteria;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:testApplicationContext.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:xmldata/newsData.xml")
@DatabaseTearDown(value = { "classpath:xmldata/newsDataUpdate.xml" }, type = DatabaseOperation.DELETE)
public class NewsDaoImplTest {
    @Autowired
    private NewsDao newsDao;

    @Test
    public void testTotalCount() throws DaoException {
        int totalCount = newsDao.selectTotalCount();
        Assert.assertTrue(totalCount > 0);
    }

    @Test
    public void testSelectByAuthor() throws DaoException, InterruptedException {
        Long authorId = 1L;
        List<News> news = newsDao.selectNewsByAuthor(authorId);
        Assert.assertEquals(1, news.size());
    }

    @Test
    public void testSelectById() throws DaoException  {
        Long newsId = 2L;
        News news = newsDao.selectById(newsId);
        Assert.assertEquals(newsId, news.getId());
        Assert.assertEquals(1L, (long)news.getPreviousId());
        Assert.assertEquals(3L, (long)news.getNextId());
    }

    @Test
    @ExpectedDatabase(value = "classpath:xmldata/newsDataUpdate.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = { "classpath:xmldata/newsDataUpdate.xml" }, type = DatabaseOperation.DELETE)
    public void testUpdate() throws DaoException {
        Long newsId = 1L;
        String text = "Updated text";
        News news = new News();
        news.setId(newsId);
        news.setTitle(text);
        news.setShortText(text);
        news.setFullText(text);
        newsDao.update(news);
        News updatedNews = newsDao.selectById(newsId);
        Assert.assertEquals(newsId, updatedNews.getId());
        Assert.assertEquals(text, updatedNews.getTitle());
        Assert.assertEquals(text, updatedNews.getShortText());
        Assert.assertEquals(text, updatedNews.getFullText());
    }

    @Test
    public void testInsert() throws DaoException {
        String text = "Breaking news";
        News news = new News();
        news.setTitle(text);
        news.setShortText(text);
        news.setFullText(text);
        Long id = newsDao.insert(news);
        Assert.assertTrue(id > 0);
        News insertedNews = newsDao.selectById(id);
        Assert.assertNotNull(insertedNews);
        Assert.assertEquals(text, insertedNews.getTitle());
        Assert.assertEquals(text, insertedNews.getShortText());
        Assert.assertEquals(text, insertedNews.getFullText());
    }

    @Test
    public void testDelete() throws DaoException {
        Long newsId = 2L;
        newsDao.delete(newsId);
        Assert.assertNull(newsDao.selectById(newsId));
    }

    @Test
    public void testSelectAll() throws DaoException {
        Long page = 1L;
        Long limit = 3L;
        List<News> news = newsDao.selectAllNews(page, limit);
        Assert.assertEquals(3, news.size());
    }

    @Test
    public void testSelectBySearchCriteria() throws DaoException {
        Long authorId = 2L;
        Long tagId = 1L;
        Long expectedNewsId = 2L;
        List<Long> tagsId = new ArrayList<>();
        List<Long> authorsId = new ArrayList<>();
        tagsId.add(tagId);
        authorsId.add(authorId);
        SearchCriteria searchCriteria = new SearchCriteria(tagsId, authorsId);
        List<News> news = newsDao.selectBySearchCriteria(searchCriteria);
        Assert.assertTrue(news.size() > 0);
        Assert.assertEquals(expectedNewsId, news.get(0).getId());
    }
}
