package by.epam.news.service.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.TagDao;
import by.epam.news.domain.Tag;
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

public class TagServiceImplTest {
    @Mock
    private TagDao tagDao;

    @InjectMocks
    private TagServiceImpl tagService ;

    private Long tagId = 1L;
    private String tagName = "hockey";

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindTagById() throws ServiceException, DaoException {
        Tag expectedTag = new Tag(tagId, tagName);
        Mockito.when(tagDao.selectById(tagId)).thenReturn(expectedTag);
        Tag tag = tagService.findTagById(tagId);
        Mockito.verify(tagDao).selectById(tagId);
        Assert.assertEquals(expectedTag, tag);
    }

    @Test
    public void testFindAllWhenNotEmpty() throws DaoException, ServiceException {
        List<Tag> allTags = new ArrayList<>();
        allTags.add(new Tag(tagId, tagName));
        Mockito.when(tagDao.selectAll()).thenReturn(allTags);
        List<Tag> actualTags = tagService.findAll();
        Assert.assertNotNull(actualTags);
        Assert.assertEquals(1, actualTags.size());
        Mockito.verify(tagDao).selectAll();
    }

    @Test
    public void testFindAllWhenEmpty() throws DaoException, ServiceException {
        List<Tag> allTags = new ArrayList<>();
        Mockito.when(tagDao.selectAll()).thenReturn(allTags);
        List<Tag> actualTags = tagService.findAll();
        Assert.assertNull(actualTags);
        Mockito.verify(tagDao).selectAll();
    }

    @Test
    public void testUpdateTag() throws DaoException, ServiceException {
        Tag tag = new Tag();
        tag.setName(tagName);
        Mockito.when(tagDao.update(tag)).thenReturn(true);
        Assert.assertTrue(tagService.updateTag(tag));
        Mockito.verify(tagDao).update(tag);
    }

    @Test
    public void testAddTag() throws DaoException, ServiceException {
        Tag tag = new Tag(tagId, tagName);
        Mockito.when(tagDao.insert(tag)).thenReturn(tagId);
        Assert.assertEquals(tagId, tagService.addTag(tag));
        Mockito.verify(tagDao).insert(tag);
    }
}
