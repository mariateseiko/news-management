package by.epam.news.service.impl;

import by.epam.news.dao.AuthorDao;
import by.epam.news.dao.DaoException;
import by.epam.news.domain.Author;
import by.epam.news.service.AuthorService;
import by.epam.news.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private AuthorDao authorDao;
    private static final Logger LOG = LogManager.getLogger(AuthorServiceImpl.class);

    public void setAuthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Long addAuthor(Author author) throws ServiceException {
        try {
            return authorDao.insert(author);
        } catch (DaoException e) {
            LOG.error("Failed to add new author: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void markExpired(Long authorId) throws ServiceException {
        try {
            authorDao.updateExpired(authorId);
        } catch (DaoException e) {
            LOG.error("Failed to make author expired: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Author findAuthorById(Long authorId) throws ServiceException {
        try {
            return authorDao.selectById(authorId);
        } catch (DaoException e) {
            LOG.error("Failed to find author by id: ", e);
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
            LOG.error("Failed to find all not expired authors", e);
            throw new ServiceException(e);
        }
        return authors;
    }

    @Override
    public void updateAuthor(Author author) throws ServiceException {
        try {
           authorDao.update(author);
        } catch (DaoException e) {
            LOG.error("Failed to update author", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Author> findNewsAuthors(Long newsId) throws ServiceException {
        try {
            return authorDao.selectForNews(newsId);
        } catch (DaoException e) {
            LOG.error("Failed to find news for author", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void unlinkAllAuthorsFromNews(Long newsId) throws ServiceException {
        try {
            authorDao.unlinkAllAuthors(newsId);
        } catch (DaoException e) {
            LOG.error("Failed to unlink all authors from news", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void linkAuthorToNews(Long newsId, Long authorId) throws ServiceException {
        try {
            authorDao.linkAuthorNews(newsId, authorId);
        } catch (DaoException e) {
            LOG.error("Failed to link news to author", e);
            throw new ServiceException(e);
        }
    }
}
