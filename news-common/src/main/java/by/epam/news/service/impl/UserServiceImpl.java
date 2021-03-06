package by.epam.news.service.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.UserDao;
import by.epam.news.domain.User;
import by.epam.news.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user;
        try {
             user = userDao.selectUserByLogin(login);
            if (user != null) {
                user.setUserRoles(userDao.selectUserRoles(user.getId()));
            } else {
                throw new UsernameNotFoundException("Failed to find user with name: " + login);
            }
        } catch (DaoException e) {
            LOG.error("Failed to load user by username " + login + ": ", e);
            throw new UsernameNotFoundException("Failed to find user with name: " + login, e);
        }
        return user;
    }
}
