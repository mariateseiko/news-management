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
    private static final String SQL_SELECT_AUTHOR_BY_ID = "SELECT author_id, author_name, expired FROM " +
            "authors WHERE author_id = ?";
    private static final String SQL_UPDATE_AUTHOR = "UPDATE authors SET author_name=?, expired=? WHERE author_id=?";
    private static final String SQL_INSERT_AUTHOR = "INSERT INTO authors (author_name, expired) VALUES(?,?)";
    private static final String SQL_SELECT_AUTHOR_BY_NEWS_ID = "SELECT authors.author_id, authors.author_name, " +
            "authors.expired FROM news_authors " +
            "JOIN authors ON authors.author_id = news_authors.author_id " +
            "WHERE news_id = ?";
    private static final String SQL_SELECT_NOT_EXPIRED = "SELECT author_id, author_name, expired FROM " +
            "authors WHERE expired IS NULL";
    private static final String SQL_UPDATE_EXPIRED = "UPDATE authors SET expired=CURRENT_TIMESTAMP WHERE author_id=?";
    private static final String SQL_UNLINK_AUTHORS_NEWS = "DELETE FROM news_authors WHERE news_id=?";
    private static final String SQL_LINK_AUTHOR_NEWS = "INSERT INTO news_authors (news_id, author_id) VALUES(?,?)";

    private static final String AUTHOR_ID = "author_id";
    private static final String AUTHOR_NAME = "author_name";
    private static final String AUTHOR_EXPIRED = "expired";

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Author selectById(Long id) throws DaoException {
        Author author = null;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_AUTHOR_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                author = extractAuthorFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select entity for id: " + id, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return author;
    }

    @Override
    public void update(Author author) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_AUTHOR)) {
            statement.setString(1, author.getName());
            statement.setTimestamp(2, author.getExpired());
            statement.setLong(3, author.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update author: " + author, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    @Override
    public Long insert(Author author) throws DaoException {
        Long generatedId = -1L;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_AUTHOR, new String[]{AUTHOR_ID})) {
            statement.setString(1, author.getName());
            statement.setTimestamp(2, author.getExpired());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to insert author: " + author, e);
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
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_AUTHOR_BY_NEWS_ID)) {
            statement.setLong(1, newsId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Author author = extractAuthorFromResultSet(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select authors for news with id: " + newsId, e);
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
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_NOT_EXPIRED)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Author author = extractAuthorFromResultSet(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select not expired authors", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return authors;
    }

    @Override
    public void updateExpired(Long authorId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EXPIRED)) {
            statement.setLong(1, authorId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update expired status for author with id: " + authorId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    @Override
    public void unlinkAllAuthors(Long newsId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_UNLINK_AUTHORS_NEWS)) {
            statement.setLong(1, newsId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to unlink authors from news with id: " + newsId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    @Override
    public void linkAuthorNews(Long newsId, Long authorId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_LINK_AUTHOR_NEWS)) {
            statement.setLong(1, newsId);
            statement.setLong(2, authorId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to link news with id: " + newsId + " and author with id: " +
                    authorId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    private Author extractAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong(AUTHOR_ID));
        author.setName(resultSet.getString(AUTHOR_NAME));
        author.setExpired(resultSet.getTimestamp(AUTHOR_EXPIRED));
        return author;
    }
}
