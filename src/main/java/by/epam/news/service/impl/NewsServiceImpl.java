package by.epam.news.service.impl;

import by.epam.news.dao.*;
import by.epam.news.domain.*;
import by.epam.news.service.NewsService;
import by.epam.news.service.ServiceException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class NewsServiceImpl implements NewsService {
    private NewsDao newsDao;
    private CommentDao commentDao;
    private TagDao tagDao;
    private AuthorDao authorDao;

    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public void setAuthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
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
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public Long addNews(NewsDTO newsDTO) throws ServiceException {
        Long newsId;
        try {
            newsId = newsDao.insert(newsDTO.getNews());
            if (newsId != null) {
                newsDao.linkAuthorNews(newsId, newsDTO.getAuthor().getId());
                for (Tag tag : newsDTO.getTags()) {
                    newsDao.linkTagNews(newsId, tag.getId());
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newsId;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public NewsDTO findById(Long newsId) throws ServiceException {
        NewsDTO newsDTO = null;
        try {
            News news = newsDao.selectById(newsId);
            if (news != null) {
                Author author = authorDao.selectForNews(news.getId()).get(0);
                List<Comment> comments = commentDao.selectForNews(newsId);
                List<Tag> tags = tagDao.selectForNews(newsId);
                newsDTO = new NewsDTO(news, author, comments, tags);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newsDTO;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public List<NewsDTO> findAllNews() throws ServiceException {
        List<NewsDTO> newsDTOList;
        try {
            List<News> news = newsDao.selectAllNews();
            if (news.size() > 0) {
                newsDTOList = createNewsDTOList(news);
            } else {
                newsDTOList = null;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newsDTOList;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public List<NewsDTO> findNewsByAuthor(Long authorId) throws ServiceException {
        List<NewsDTO> newsDTOList;
        try {
            List<News> news = newsDao.selectNewsByAuthor(authorId);
            if (news.size() > 0) {
                newsDTOList = createNewsDTOList(news);
            } else {
                newsDTOList = null;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newsDTOList;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public boolean deleteNews(Long newsId) throws ServiceException {
        try {
            commentDao.deleteForNews(newsId);
            newsDao.delete(newsId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public boolean addTagsToNews(Long newsId, List<Long> tagsId) throws ServiceException {
        try {
            for (Long tagId: tagsId) {
                newsDao.linkTagNews(newsId, tagId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public boolean deleteTagFromNews(Long newsId, Long tagId) throws ServiceException {
        try {
            newsDao.unlinkTagNews(newsId, tagId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public boolean updateNews(News news) throws ServiceException {
        try {
            return newsDao.update(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<NewsDTO> findBySearchCriteria(SearchCriteria searchCriteria) throws ServiceException {
        List<NewsDTO> newsDTOList;
        try {
            List<News> news = newsDao.selectBySearchCriteria(searchCriteria);
            if (news.size() > 0) {
                newsDTOList = createNewsDTOList(news);
            } else {
                newsDTOList = null;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newsDTOList;
    }

    private List<NewsDTO> createNewsDTOList(List<News> news) throws DaoException {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        for (News newsMessage: news) {
            long newsId = newsMessage.getId();
            Author author = authorDao.selectForNews(newsMessage.getId()).get(0);
            List<Tag> tags = tagDao.selectForNews(newsId);
            NewsDTO newsDTO = new NewsDTO(newsMessage, author, tags);
            newsDTOList.add(newsDTO);
        }
        return newsDTOList;
    }
}
