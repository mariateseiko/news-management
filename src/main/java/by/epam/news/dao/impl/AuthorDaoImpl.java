package by.epam.news.dao.impl;

import by.epam.news.dao.AuthorDao;
import by.epam.news.dao.DaoException;
import by.epam.news.domain.Author;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoImpl implements AuthorDao {
    private static final String SELECT_AUTHOR_BY_ID = "SELECT author_id, author_name, expired FROM " +
            "authors WHERE author_id = ?";
    private static final String UPDATE_AUTHOR = "UPDATE authors SET author_name=?, expired=? WHERE author_id=?";
    private static final String INSERT_AUTHOR = "INSERT INTO authors (author_name, expired) VALUES(?,?)";
    private static final String SELECT_AUTHOR_BY_NEWS_ID = "SELECT authors.author_id, authors.author_name, " +
            "authors.expired FROM news_authors " +
            "JOIN authors ON authors.author_id = news_authors.author_id " +
            "WHERE news_id = ?";
    private static final String SELECT_NOT_EXPIRED = "SELECT author_id, author_name, expired FROM " +
            "authors WHERE expired IS NULL";
    private static final String UPDATE_EXPIRED = "UPDATE authors SET expired=CURRENT_TIMESTAMP WHERE author_id=?";

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Author selectById(Long id) throws DaoException {
        Author author = null;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SELECT_AUTHOR_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                author = new Author();
                author.setId(resultSet.getLong("author_id"));
                author.setName(resultSet.getString("author_name"));
                author.setExpired(resultSet.getTimestamp("expired"));
            }
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return author;
    }

    @Override
    public boolean update(Author author) throws DaoException {
        int countUpdatedRows;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_AUTHOR)) {
            statement.setString(1, author.getName());
            statement.setTimestamp(2, author.getExpired());
            statement.setLong(3, author.getId());
            countUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return countUpdatedRows > 0;
    }

    @Override
    public Long insert(Author author) throws DaoException {
        Long generatedId = -1L;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(INSERT_AUTHOR, new String[]{"author_id"})) {
            statement.setString(1, author.getName());
            statement.setTimestamp(2, author.getExpired());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return generatedId;
    }

    @Override
    public List<Author> selectForNews(Long newsId) throws DaoException {
        List<Author> authors = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SELECT_AUTHOR_BY_NEWS_ID)) {
            statement.setLong(1, newsId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getLong("author_id"));
                author.setName(resultSet.getString("author_name"));
                author.setExpired(resultSet.getTimestamp("expired"));
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return authors;
    }

    @Override
    public List<Author> selectNotExpired() throws DaoException {
        List<Author> authors = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SELECT_NOT_EXPIRED)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getLong("author_id"));
                author.setName(resultSet.getString("author_name"));
                author.setExpired(resultSet.getTimestamp("expired"));
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return authors;
    }

    @Override
    public boolean updateExpired(Long authorId) throws DaoException {
        int countUpdatedRows;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EXPIRED)) {
            statement.setLong(1, authorId);
            countUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return countUpdatedRows > 0;
    }
}
