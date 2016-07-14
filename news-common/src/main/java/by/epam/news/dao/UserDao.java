package by.epam.news.dao;

import by.epam.news.domain.User;

import java.util.List;

/**
 * Interface representing common actions for Retrieving user-related info
 */
public interface UserDao {
    /**
     * Retrieves a user object by user's login
     * @param login user's login
     * @return fully filled user object
     */
    User selectUserByLogin(String login) throws DaoException;

    /**
     * Retrieves a list of roles for a given user
     * @param userId id of the user
     * @return list of roles names
     * @throws DaoException if exception occured on current level
     */
    List<String> selectUserRoles(Long userId) throws DaoException;
}
