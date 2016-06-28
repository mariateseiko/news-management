package by.epam.news.service.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.TagDao;
import by.epam.news.domain.Tag;
import by.epam.news.service.ServiceException;
import by.epam.news.service.TagService;

import java.util.List;

public class TagServiceImpl implements TagService {
    private TagDao tagDao;

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Long addTag(Tag tag) throws ServiceException {
        try {
            return tagDao.insert(tag);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Tag findTagById(Long tagId) throws ServiceException {
        try {
            return tagDao.selectById(tagId);
        } catch (DaoException e) {
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
            throw new ServiceException(e);
        }
        return tags;
    }

    @Override
    public boolean updateTag(Tag tag) throws ServiceException {
        try {
            return tagDao.update(tag);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteTag(Long tagId) throws ServiceException {
        try {
            tagDao.delete(tagId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
