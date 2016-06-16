package by.epam.news.dao.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.TagDao;
import by.epam.news.domain.Tag;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:testApplicationContext.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:xmldata/tagData.xml")
@DatabaseTearDown(value = { "classpath:xmldata/tagData.xml" }, type = DatabaseOperation.DELETE)
public class TagDaoImplTest  {
    @Autowired
    private TagDao tagDao;

    @Test
    public void testSelectById() throws DaoException {
        Long tagId = 1L;
        Tag tag = tagDao.selectById(tagId);
        Assert.assertEquals("hockey", tag.getName());
        Assert.assertEquals(tagId, tag.getId());
    }

    @Test
    @ExpectedDatabase(value = "classpath:xmldata/tagDataUpdate.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = { "classpath:xmldata/tagDataUpdate.xml" }, type = DatabaseOperation.DELETE)
    public void testUpdate() throws DaoException {
        Tag tag = new Tag();
        Long tagId = 1L;
        tag.setId(tagId);
        tag.setName("Some test tag");
        Assert.assertTrue(tagDao.update(tag));
    }

    @Test
    public void testSelectAll() throws DaoException {
        List<Tag> tags = tagDao.selectAll();
        Assert.assertTrue(tags.size() > 0);
    }

    @Test
    public void testInsert() throws DaoException {
        Tag tag = new Tag();
        tag.setName("New tag");
        Long id = tagDao.insert(tag);
        Assert.assertTrue(id > 0);
        Tag insertedTag = tagDao.selectById(id);
        Assert.assertEquals(tag.getName(), insertedTag.getName());
        Assert.assertEquals(id, insertedTag.getId());
    }

    @Test
    public void testSelectForNews() throws DaoException {
        Long newsId = 2L;
        List<Tag> tags = tagDao.selectForNews(newsId);
        Assert.assertEquals(2, tags.size());
    }
}
