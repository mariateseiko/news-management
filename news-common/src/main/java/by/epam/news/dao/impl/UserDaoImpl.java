package by.epam.news.dao.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.UserDao;
import by.epam.news.domain.User;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final String SQL_SELECT_USER = "SELECT user_id, user_name, login, password FROM users " +
            "WHERE user_name=?";
    private static final String SQL_SELECT_USER_ROLES = "SELECT role_name FROM roles WHERE user_id=?";
    private DataSource dataSource;
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User selectUserByLogin(String login) throws DaoException {
        User user = null;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setFullName(resultSet.getString("user_name"));
                user.setUserName(resultSet.getString("login"));
                user.setHashedPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select user with login: " + login, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return user;
    }

    @Override
    public List<String> selectUserRoles(Long userId) throws DaoException {
        List<String> roles = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_ROLES)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roles.add(resultSet.getString("role_name"));
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select roles for user with id: " + userId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return roles;
    }
}
