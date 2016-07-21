package by.epam.news.service.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.TagDao;
import by.epam.news.domain.Tag;
import by.epam.news.service.ServiceException;
import by.epam.news.service.TagService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TagServiceImpl implements TagService {
    private TagDao tagDao;
    private static final Logger LOG = LogManager.getLogger(TagServiceImpl.class);

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Long addTag(Tag tag) throws ServiceException {
        try {
            return tagDao.insert(tag);
        } catch (DaoException e) {
            LOG.error("Failed to add new tag: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Tag findTagById(Long tagId) throws ServiceException {
        try {
            return tagDao.selectById(tagId);
        } catch (DaoException e) {
            LOG.error("Failed to find tag by id: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Tag> findAll() throws ServiceException {
        List<Tag> tags;
        try {
            tags =  tagDao.selectAll();
            if (tags.size() == 0) {
                tags = null;
            }
        } catch (DaoException e) {
            LOG.error("Failed to find all tags: ", e);
            throw new ServiceException(e);
        }
        return tags;
    }

    @Override
    public void updateTag(Tag tag) throws ServiceException {
        try {
            tagDao.update(tag);
        } catch (DaoException e) {
            LOG.error("Failed to update tag: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteTag(Long tagId) throws ServiceException {
        try {
            tagDao.delete(tagId);
        } catch (DaoException e) {
            LOG.error("Failed to delete tag: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Tag> findNewsTags(Long newsId) throws ServiceException {
        try {
            return tagDao.selectForNews(newsId);
        } catch (DaoException e) {
            LOG.error("Failed to find news' tags: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void unlinkAllTagsFromNews(Long newsId) throws ServiceException {
        try {
            tagDao.unlinkAllTags(newsId);
        } catch (DaoException e) {
            LOG.error("Failed to unlink all tags from news: ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void linkTagToNews(Long newsId, Long tagId) throws ServiceException {
        try {
            tagDao.linkTagNews(newsId, tagId);
        } catch (DaoException e) {
            LOG.error("Failed to link tag to news: ", e);
            throw new ServiceException(e);
        }
    }
}
