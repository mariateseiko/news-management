package by.epam.news.service.impl;

import by.epam.news.dao.AuthorDao;
import by.epam.news.dao.DaoException;
import by.epam.news.domain.Author;
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

public class AuthorServiceImplTest {
    @Mock
    private AuthorDao authorDao;

    @InjectMocks
    private AuthorServiceImpl authorService ;

    private Long authorId = 1L;
    private String authorName = "John Doe";

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAuthorById() throws ServiceException, DaoException {
        Author expectedAuthor = new Author(authorId, authorName, null);
        Mockito.when(authorDao.selectById(authorId)).thenReturn(expectedAuthor);
        Author author = authorService.findAuthorById(authorId);
        Mockito.verify(authorDao).selectById(authorId);
        Assert.assertEquals(expectedAuthor, author);
    }

    @Test
    public void testFindAllWhenNotEmpty() throws DaoException, ServiceException {
        List<Author> notExpiredAuthors = new ArrayList<>();
        notExpiredAuthors.add(new Author(authorId, authorName, null));
        Mockito.when(authorDao.selectNotExpired()).thenReturn(notExpiredAuthors);
        List<Author> actualAuthors = authorService.findNotExpiredAuthors();
        Assert.assertNotNull(actualAuthors);
        Assert.assertEquals(1, actualAuthors.size());
        Mockito.verify(authorDao).selectNotExpired();
    }

    @Test
    public void testFindAllWhenEmpty() throws DaoException, ServiceException {
        List<Author> notExpiredAuthors = new ArrayList<>();
        Mockito.when(authorDao.selectNotExpired()).thenReturn(notExpiredAuthors);
        List<Author> actualAuthors = authorService.findNotExpiredAuthors();
        Assert.assertNull(actualAuthors);
        Mockito.verify(authorDao).selectNotExpired();
    }

    @Test
    public void testAddAuthor() throws DaoException, ServiceException {
        Author author = new Author(authorId, authorName, null);
        Mockito.when(authorDao.insert(author)).thenReturn(authorId);
        Assert.assertEquals(authorId, authorService.addAuthor(author));
        Mockito.verify(authorDao).insert(author);
    }

    @Test
    public void testMarkExpired() throws DaoException, ServiceException {
        authorService.markExpired(authorId);
        Mockito.verify(authorDao).updateExpired(authorId);
    }

    @Test
    public void testUpdateAuthor() throws DaoException, ServiceException {
        Author author = new Author(authorId, authorName, null);
        authorService.updateAuthor(author);
        Mockito.verify(authorDao).update(author);
    }

    @Test
    public void testFindNewsAuthors() throws DaoException, ServiceException {
        List<Author> notExpiredAuthors = new ArrayList<>();
        notExpiredAuthors.add(new Author(authorId, authorName, null));
        Mockito.when(authorDao.selectForNews(authorId)).thenReturn(notExpiredAuthors);
        Assert.assertEquals(notExpiredAuthors, authorService.findNewsAuthors(authorId));
        Mockito.verify(authorDao).selectForNews(authorId);
    }

    @Test
    public void testLinkAuthorToNews() throws DaoException, ServiceException {
        authorService.linkAuthorToNews(authorId, authorId);
        Mockito.verify(authorDao).linkAuthorNews(authorId, authorId);
    }

    @Test
    public void testUnlinkAuthorsFromNews() throws DaoException, ServiceException {
        authorService.unlinkAllAuthorsFromNews(authorId);
        Mockito.verify(authorDao).unlinkAllAuthors(authorId);
    }
}
