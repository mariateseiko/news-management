package by.epam.news.service.impl;

import by.epam.news.dao.*;
import by.epam.news.domain.*;
import by.epam.news.service.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;

public class NewsServiceImplTest {
    @Mock
    private NewsDao newsDao;

    @InjectMocks
    private NewsServiceImpl newsService;

    private Long id = 1L;
    private String text = "Some text";

    @Before
    public void initMocks() throws DaoException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCountNews() throws DaoException, ServiceException {
        Long totalNewsCount = 5L;
        Mockito.when(newsDao.selectTotalCount()).thenReturn(totalNewsCount);
        Assert.assertEquals(totalNewsCount, newsService.countNews());
        Mockito.verify(newsDao).selectTotalCount();
    }

    @Test
    public void testFilteredNews() throws DaoException, ServiceException {
        Long totalNewsCount = 5L;
        Mockito.when(newsDao.selectFilteredCount(anyObject())).thenReturn(totalNewsCount);
        Assert.assertEquals(totalNewsCount, newsService.countFilteredNews(anyObject()));
        Mockito.verify(newsDao).selectFilteredCount(anyObject());
    }

    @Test
    public void testAddNews() throws DaoException, ServiceException {
        News news = new News(text, text, text);
        Mockito.when(newsDao.insert(news)).thenReturn(id);
        Assert.assertEquals(id, newsService.addNews(news));
        Mockito.verify(newsDao).insert(news);
    }

    @Test
    public void testFindById() throws DaoException, ServiceException {
        News news = new News(text, text, text);
        news.setId(id);
        Mockito.when(newsDao.selectById(id)).thenReturn(news);
        News foundNews = newsService.findNewsById(id);
        Assert.assertEquals(id, foundNews.getId());
        Assert.assertEquals(text, foundNews.getTitle());
        Assert.assertEquals(text, foundNews.getShortText());
        Assert.assertEquals(text, foundNews.getFullText());
        Mockito.verify(newsDao).selectById(id);
    }

    @Test
    public void testFindAll() throws DaoException, ServiceException {
        List<News> newsList = new ArrayList<>();
        News news = new News(text, text, text);
        news.setId(id);
        newsList.add(news);
        Mockito.when(newsService.findAllNews(anyLong(), anyLong())).thenReturn(newsList);
        List<News> foundNewsList = newsService.findAllNews(id, id);
        Assert.assertEquals(1, foundNewsList.size());
        Mockito.verify(newsDao).selectAllNews(id, id);
    }

    /*@Test
    public void testFindNewsByAuthor() throws DaoException, ServiceException {
        List<News> newsList = new ArrayList<>();
        News news = new News(text, text, text);
        news.setId(id);
        newsList.add(news);
        Mockito.when(newsDao.selectNewsByAuthor(id)).thenReturn(newsList);
        List<News> foundNewsList = newsService.findNewsByAuthor(id);
        Assert.assertEquals(1, newsDTOList.size());
        Mockito.verify(newsDao).selectNewsByAuthor(id);
        Mockito.verify(authorDao).selectForNews(id);
        Mockito.verify(tagDao).selectForNews(id);
    }*/

    @Test
    public void testDeleteNews() throws DaoException, ServiceException {
        newsService.deleteNews(id);
        Mockito.verify(newsDao).delete(id);
    }

    @Test
    public void testUpdateNews() throws DaoException, ServiceException {
        News news = new News();
        newsService.updateNews(news);
        Mockito.verify(newsDao).update(news);
    }

    @Test
    public void testFindBySearchCriteria() throws DaoException, ServiceException {
        List<Tag> tags = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        tags.add(new Tag(id));
        authors.add(new Author(id));
        SearchCriteria searchCriteria = new SearchCriteria(tags, authors, id, id);
        News news = new News(text, text, text);
        news.setId(id);
        List<News> newsList = new ArrayList<>();
        newsList.add(news);
        Mockito.when(newsDao.selectBySearchCriteria(searchCriteria)).thenReturn(newsList);
        Assert.assertTrue(newsService.findNewsBySearchCriteria(searchCriteria).size() > 0);
        Mockito.verify(newsDao).selectBySearchCriteria(searchCriteria);
    }

    @Test
    public void testNotFoundBySearchCriteria() throws DaoException, ServiceException {
        SearchCriteria searchCriteria = new SearchCriteria();
        Mockito.when(newsDao.selectBySearchCriteria(searchCriteria)).thenReturn(new ArrayList<>());
        Assert.assertEquals(0, newsService.findNewsBySearchCriteria(searchCriteria).size());
        Mockito.verify(newsDao).selectBySearchCriteria(searchCriteria);
    }
}
