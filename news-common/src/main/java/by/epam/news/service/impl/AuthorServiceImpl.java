package by.epam.news.service.impl;

import by.epam.news.dao.AuthorDao;
import by.epam.news.dao.DaoException;
import by.epam.news.domain.Author;
import by.epam.news.service.AuthorService;
import by.epam.news.service.ServiceException;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private AuthorDao authorDao;

    public void setAuthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Long addAuthor(Author author) throws ServiceException {
        try {
            return authorDao.insert(author);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void markExpired(Long authorId) throws ServiceException {
        try {
            authorDao.updateExpired(authorId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Author findAuthorById(Long authorId) throws ServiceException {
        try {
            return authorDao.selectById(authorId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Author> findNotExpiredAuthors() throws ServiceException {
        List<Author> authors;
        try {
            authors = authorDao.selectNotExpired();
            if (authors.isEmpty()) {
                authors = null;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return authors;
    }

    @Override
    public void updateAuthor(Author author) throws ServiceException {
        try {
           authorDao.update(author);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Author> findNewsAuthors(Long newsId) throws ServiceException {
        try {
            return authorDao.selectForNews(newsId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unlinkAllAuthorsFromNews(Long newsId) throws ServiceException {
        try {
            authorDao.unlinkAllAuthors(newsId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void linkAuthorToNews(Long newsId, Long authorId) throws ServiceException {
        try {
            authorDao.linkAuthorNews(newsId, authorId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
