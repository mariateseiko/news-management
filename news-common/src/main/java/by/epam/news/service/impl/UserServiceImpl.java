package by.epam.news.service.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.UserDao;
import by.epam.news.domain.User;
import by.epam.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

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
            throw new UsernameNotFoundException("Failed to find user with name: " + login, e);
        }
        return user;
    }
}
