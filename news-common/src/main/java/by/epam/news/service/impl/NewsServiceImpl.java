package by.epam.news.service.impl;

import by.epam.news.dao.*;
import by.epam.news.domain.*;
import by.epam.news.service.NewsService;
import by.epam.news.service.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class NewsServiceImpl implements NewsService {
    private NewsDao newsDao;

    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public Integer countNews() throws ServiceException {
        try {
            return newsDao.selectTotalCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteNews(Long newsId) throws ServiceException {
        try {
            newsDao.delete(newsId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findNewsBySearchCriteria(SearchCriteria searchCriteria) throws ServiceException {
        try {
            return newsDao.selectBySearchCriteria(searchCriteria);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public News findNewsById(Long newsId) throws ServiceException {
        try {
            return newsDao.selectById(newsId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findAllNews(Long page, Long limit) throws ServiceException {
        try {
            return newsDao.selectAllNews(page, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long addNews(News news) throws ServiceException {
        try {
            return newsDao.insert(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateNews(News news) throws ServiceException {
        try {
            newsDao.update(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
