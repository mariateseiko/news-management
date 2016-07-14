package by.epam.news.dao.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.UserDao;
import by.epam.news.domain.User;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
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
public class UserDaoImplTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void testSelectByLoginPassword() throws DaoException {
        String login = "user";
        User user = userDao.selectUserByLogin(login);
        Assert.assertEquals(login, user.getUsername());
    }

    @Test
    public void testSelectUserRoles() throws DaoException {
        Long userId = 1L;
        List<String> roles = userDao.selectUserRoles(userId);
        Assert.assertEquals(1, roles.size());
        Assert.assertEquals("ROLE_ADMIN", roles.get(0));
    }
}
