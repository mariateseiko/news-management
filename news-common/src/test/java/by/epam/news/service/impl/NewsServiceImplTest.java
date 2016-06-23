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

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;

public class NewsServiceImplTest {
    @Mock
    private NewsDao newsDao;
    @Mock
    private CommentDao commentDao;
    @Mock
    private TagDao tagDao;
    @Mock
    private AuthorDao authorDao;
    @InjectMocks
    private NewsServiceImpl newsService;

    private Long id = 1L;
    private String text = "Some text";

    @Before
    public void initMocks() throws DaoException {
        MockitoAnnotations.initMocks(this);
        Author author = new Author(id, text, null);
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(id, text));
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(text, id));
        Mockito.when(authorDao.selectForNews(id)).thenReturn(authors);
        Mockito.when(tagDao.selectForNews(id)).thenReturn(tags);
        Mockito.when(commentDao.selectForNews(id)).thenReturn(comments);
    }

    @Test
    public void testCountNews() throws DaoException, ServiceException {
        Integer totalNewsCount = 5;
        Mockito.when(newsDao.selectTotalCount()).thenReturn(totalNewsCount);
        Assert.assertEquals(totalNewsCount, newsService.countNews());
        Mockito.verify(newsDao).selectTotalCount();
    }

    @Test
    public void testAddNews() throws DaoException, ServiceException {
        News news = new News(text, text, text);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(id, text));
        NewsDTO newsDTO = new NewsDTO(news, new Author(id, text, null),
                new ArrayList<>(), tags);
        Mockito.when(newsDao.insert(news)).thenReturn(id);
        Mockito.when(newsDao.linkAuthorNews(anyObject(), anyObject())).thenReturn(true);
        Mockito.when(newsDao.linkTagNews(anyObject(), anyObject())).thenReturn(true);
        Assert.assertEquals(id, newsService.addNews(newsDTO));
        Mockito.verify(newsDao).insert(news);
        Mockito.verify(newsDao).linkTagNews(anyObject(), anyObject());
        Mockito.verify(newsDao).linkAuthorNews(anyObject(), anyObject());
    }

    @Test
    public void testFindById() throws DaoException, ServiceException {
        News news = new News(text, text, text);
        news.setId(id);
        Mockito.when(newsDao.selectById(id)).thenReturn(news);
        NewsDTO newsDTO = newsService.findById(id);
        Assert.assertEquals(id, newsDTO.getNews().getId());
        Assert.assertNotNull(newsDTO.getAuthor());
        Assert.assertNotNull(newsDTO.getComments());
        Assert.assertNotNull(newsDTO.getTags());
        Mockito.verify(newsDao).selectById(id);
        Mockito.verify(authorDao).selectForNews(id);
        Mockito.verify(commentDao).selectForNews(id);
        Mockito.verify(tagDao).selectForNews(id);
    }

    @Test
    public void testFindAll() throws DaoException, ServiceException {
        List<News> newsList = new ArrayList<>();
        News news = new News(text, text, text);
        news.setId(id);
        newsList.add(news);
        Mockito.when(newsDao.selectAllNews(anyLong(), anyLong())).thenReturn(newsList);
        List<NewsDTO> newsDTOList = newsService.findAllNews(id, id);
        Assert.assertEquals(1, newsDTOList.size());
        Mockito.verify(authorDao).selectForNews(id);
        Mockito.verify(tagDao).selectForNews(id);
    }

    @Test
    public void testFindNewsByAuthor() throws DaoException, ServiceException {
        List<News> newsList = new ArrayList<>();
        News news = new News(text, text, text);
        news.setId(id);
        newsList.add(news);
        Mockito.when(newsDao.selectNewsByAuthor(id)).thenReturn(newsList);
        List<NewsDTO> newsDTOList = newsService.findNewsByAuthor(id);
        Assert.assertEquals(1, newsDTOList.size());
        Mockito.verify(newsDao).selectNewsByAuthor(id);
        Mockito.verify(authorDao).selectForNews(id);
        Mockito.verify(tagDao).selectForNews(id);
    }

    @Test
    public void testDeleteNews() throws DaoException, ServiceException {
        Mockito.when(newsDao.delete(id)).thenReturn(true);
        Mockito.when(commentDao.deleteForNews(id)).thenReturn(true);
        Assert.assertTrue(newsService.deleteNews(id));
        Mockito.verify(newsDao).delete(id);
        Mockito.verify(commentDao).deleteForNews(id);
    }

    @Test
    public void testAddTagsToNews() throws DaoException, ServiceException {
        Mockito.when(newsDao.linkTagNews(anyLong(), anyLong())).thenReturn(true);
        List<Long> tagsId = new ArrayList<>();
        tagsId.add(id);
        Assert.assertTrue(newsService.addTagsToNews(id, tagsId));
        Mockito.verify(newsDao).linkTagNews(anyLong(), anyLong());
    }

    @Test
    public void testDeleteTagFromNews() throws DaoException, ServiceException {
        Mockito.when(newsDao.unlinkTagNews(anyLong(), anyLong())).thenReturn(true);
        Assert.assertTrue(newsService.deleteTagFromNews(id, id));
        Mockito.verify(newsDao).unlinkTagNews(id, id);
    }

    @Test
    public void testUpdateNews() throws DaoException, ServiceException {
        News news = new News();
        Mockito.when(newsDao.update(news)).thenReturn(true);
        Assert.assertTrue(newsService.updateNews(news));
        Mockito.verify(newsDao).update(news);
    }

    @Test
    public void testFindBySearchCriteria() throws DaoException, ServiceException {
        List<Long> authorsId = new ArrayList<>();
        authorsId.add(id);
        List<Long> tagsId = new ArrayList<>();
        tagsId.add(id);
        SearchCriteria searchCriteria = new SearchCriteria(authorsId, tagsId);
        News news = new News(text, text, text);
        news.setId(id);
        List<News> newsList = new ArrayList<>();
        newsList.add(news);
        Mockito.when(newsDao.selectBySearchCriteria(searchCriteria)).thenReturn(newsList);
        Assert.assertTrue(newsService.findBySearchCriteria(searchCriteria).size() > 0);
        Mockito.verify(newsDao).selectBySearchCriteria(searchCriteria);
        Mockito.verify(authorDao).selectForNews(id);
        Mockito.verify(tagDao).selectForNews(id);
    }

    @Test
    public void testNotFoundBySearchCriteria() throws DaoException, ServiceException {
        SearchCriteria searchCriteria = new SearchCriteria();
        Mockito.when(newsDao.selectBySearchCriteria(searchCriteria)).thenReturn(new ArrayList<>());
        Assert.assertNull(newsService.findBySearchCriteria(searchCriteria));
        Mockito.verify(newsDao).selectBySearchCriteria(searchCriteria);
        Mockito.verifyNoMoreInteractions(authorDao);
        Mockito.verifyNoMoreInteractions(tagDao);
    }
}
