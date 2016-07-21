package by.epam.news.service.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.NewsDao;
import by.epam.news.domain.News;
import by.epam.news.domain.SearchCriteria;
import by.epam.news.service.NewsService;
import by.epam.news.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class NewsServiceImpl implements NewsService {
    private NewsDao newsDao;
    private static final Logger LOG = LogManager.getLogger(NewsServiceImpl.class);

    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public Long countNews() throws ServiceException {
        try {
            return newsDao.selectTotalCount();
        } catch (DaoException e) {
            LOG.error("Failed to count all news: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Long countFilteredNews(SearchCriteria searchCriteria) throws ServiceException {
        try {
            return newsDao.selectFilteredCount(searchCriteria);
        } catch (DaoException e) {
            LOG.error("Failed to count filtered news: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteNews(Long newsId) throws ServiceException {
        try {
            newsDao.delete(newsId);
        } catch (DaoException e) {
            LOG.error("Failed to delete news message: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findNewsBySearchCriteria(SearchCriteria searchCriteria) throws ServiceException {
        try {
            return newsDao.selectBySearchCriteria(searchCriteria);
        } catch (DaoException e) {
            LOG.error("Failed to find news by search criteria: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public News findNewsById(Long newsId) throws ServiceException {
        try {
            return newsDao.selectById(newsId);
        } catch (DaoException e) {
            LOG.error("Failed to find news by id: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findAllNews(Long page, Long limit) throws ServiceException {
        try {
            return newsDao.selectAllNews(page, limit);
        } catch (DaoException e) {
            LOG.error("Failed to find all news messages: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Long addNews(News news) throws ServiceException {
        try {
            return newsDao.insert(news);
        } catch (DaoException e) {
            LOG.error("Failed to add news message: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateNews(News news) throws ServiceException {
        try {
            newsDao.update(news);
        } catch (DaoException e) {
            LOG.error("Failed to update news message: ", e);
            throw new ServiceException(e);
        }
    }
}
