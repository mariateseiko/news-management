package by.epam.news.service.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.domain.*;
import by.epam.news.service.*;
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

public class NewsServiceFacadeTest {
    @Mock
    private NewsService newsService;
    @Mock
    private CommentService commentService;
    @Mock
    private TagService tagService;
    @Mock
    private AuthorService authorService;

    @InjectMocks
    private NewsServiceFacadeImpl newsServiceFacade;

    private Long id = 1L;
    private String text = "Some text";

    @Before
    public void initMocks() throws ServiceException{
        MockitoAnnotations.initMocks(this);
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(id, text, null));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(id, text));
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(id, text));
        Mockito.when(authorService.findNewsAuthors(id)).thenReturn(authors);
        Mockito.when(tagService.findNewsTags(id)).thenReturn(tags);
        Mockito.when(commentService.findCommentForNews(id)).thenReturn(comments);
    }

    @Test
    public void testAddNews() throws DaoException, ServiceException {
        News news = new News(text, text, text);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(id, text));
        NewsDTO newsDTO = new NewsDTO(news, new Author(id, text, null),
                new ArrayList<>(), tags);
        Mockito.when(newsService.addNews(news)).thenReturn(id);
        Assert.assertEquals(id, newsServiceFacade.saveNews(newsDTO));
        Mockito.verify(newsService).addNews(news);
        Mockito.verify(tagService).linkTagToNews(anyObject(), anyObject());
        Mockito.verify(authorService).linkAuthorToNews(anyObject(), anyObject());
    }

    @Test
    public void testUpdateNews() throws DaoException, ServiceException {
        News news = new News(text, text, text);
        news.setId(id);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(id, text));
        NewsDTO newsDTO = new NewsDTO(news, new Author(id, text, null),
                new ArrayList<>(), tags);
        newsServiceFacade.saveNews(newsDTO);
        Mockito.verify(newsService).updateNews(news);
        Mockito.verify(tagService).unlinkAllTagsFromNews(id);
        Mockito.verify(authorService).unlinkAllAuthorsFromNews(id);
        Mockito.verify(tagService).linkTagToNews(id, id);
        Mockito.verify(authorService).linkAuthorToNews(id, id);
    }

    @Test
    public void testFindById() throws DaoException, ServiceException {
        News news = new News(text, text, text);
        news.setId(id);
        Mockito.when(newsService.findNewsById(id)).thenReturn(news);
        NewsDTO newsDTO = newsServiceFacade.findById(id);
        Assert.assertEquals(id, newsDTO.getNews().getId());
        Assert.assertNotNull(newsDTO.getAuthor());
        Assert.assertNotNull(newsDTO.getComments());
        Assert.assertNotNull(newsDTO.getTags());
        Mockito.verify(newsService).findNewsById(id);
        Mockito.verify(authorService).findNewsAuthors(id);
        Mockito.verify(commentService).findCommentForNews(id);
        Mockito.verify(tagService).findNewsTags(id);
    }

    @Test
    public void testFindAll() throws DaoException, ServiceException {
        List<News> newsList = new ArrayList<>();
        News news = new News(text, text, text);
        news.setId(id);
        newsList.add(news);
        Mockito.when(newsService.findAllNews(anyLong(), anyLong())).thenReturn(newsList);
        List<NewsDTO> newsDTOList = newsServiceFacade.findAllNews(id, id);
        Assert.assertEquals(1, newsDTOList.size());
        Mockito.verify(authorService).findNewsAuthors(id);
        Mockito.verify(tagService).findNewsTags(id);
    }

    @Test
    public void testDelete() throws DaoException, ServiceException {
        newsServiceFacade.deleteNews(id);
        Mockito.verify(newsService).deleteNews(id);
        Mockito.verify(commentService).deleteCommentsForNews(id);
    }

    @Test
    public void testFindBySearchCriteria() throws DaoException, ServiceException {
        List<Long> authorsId = new ArrayList<>();
        authorsId.add(id);
        List<Long> tagsId = new ArrayList<>();
        tagsId.add(id);
        SearchCriteria searchCriteria = new SearchCriteria(authorsId, tagsId);
        List<News> newsList = new ArrayList<>();
        News news = new News(text, text, text);
        news.setId(id);
        newsList.add(news);
        Mockito.when(newsService.findNewsBySearchCriteria(searchCriteria)).thenReturn(newsList);
        List<NewsDTO> newsDTOList = newsServiceFacade.findBySearchCriteria(searchCriteria);
        Assert.assertEquals(1, newsDTOList.size());
        Mockito.verify(authorService).findNewsAuthors(id);
        Mockito.verify(tagService).findNewsTags(id);
    }
    /*@Test
    public void testFindNewsByAuthor() throws DaoException, ServiceException {
        List<News> newsList = new ArrayList<>();
        News news = new News(text, text, text);
        news.setId(id);
        newsList.add(news);
        Mockito.when(newsService.fiNewsByAuthor(id)).thenReturn(newsList);
        List<NewsDTO> newsDTOList = newsService.findNewsByAuthor(id);
        Assert.assertEquals(1, newsDTOList.size());
        Mockito.verify(newsDao).selectNewsByAuthor(id);
        Mockito.verify(authorDao).selectForNews(id);
        Mockito.verify(tagDao).selectForNews(id);
    }*/
}
