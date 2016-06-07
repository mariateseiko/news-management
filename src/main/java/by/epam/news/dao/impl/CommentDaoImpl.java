package by.epam.news.dao.impl;

import by.epam.news.dao.CommentDao;
import by.epam.news.dao.DaoException;
import by.epam.news.domain.Comment;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentDao {
    private static final String SELECT_COMMENT_BY_ID = "SELECT comment_id, comment_text, " +
            "creation_date, news_id FROM comments WHERE comment_id=?";
    private static final String UPDATE_COMMENT = "UPDATE comments SET comment_text=? WHERE comment_id = ?";
    private static final String SELECT_ALL_COMMENTS_BY_NEWS = "SELECT comment_id, comment_text, creation_date, news_id " +
            "FROM comments WHERE news_id=?";
    private static final String INSERT_COMMENT = "INSERT INTO comments (comment_text, news_id, creation_date) " +
            "VALUES(?, ?, CURRENT_TIMESTAMP)";
    private static final String DELETE_COMMENT = "DELETE FROM comments WHERE comment_id=?";
    private static final String DELETE_COMMENTS_BY_NEWS_ID = "DELETE FROM comments WHERE news_id=?";

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Comment selectById(Long id) throws DaoException {
        Comment comment = null;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COMMENT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                comment = new Comment();
                comment.setId(resultSet.getLong("comment_id"));
                comment.setCommentText(resultSet.getString("comment_text"));
                comment.setCreationDate(resultSet.getTimestamp("creation_date"));
                comment.setNewsId(resultSet.getLong("news_id"));
            }
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return comment;
    }

    @Override
    public boolean update(Comment comment) throws DaoException {
        int countUpdatedRows;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_COMMENT)) {
            statement.setString(1, comment.getCommentText());
            statement.setLong(2, comment.getId());
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

    public List<Comment> selectForNews(Long newsId) throws DaoException {
        List<Comment> comments = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_COMMENTS_BY_NEWS)) {
            statement.setLong(1, newsId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getLong("comment_id"));
                comment.setCommentText(resultSet.getString("comment_text"));
                comment.setCreationDate(resultSet.getTimestamp("creation_date"));
                comment.setNewsId(resultSet.getLong("news_id"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        }
        return comments;
    }

    @Override
    public Long insert(Comment comment) throws DaoException {
        Long generatedId = -1L;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(INSERT_COMMENT, new String[]{"comment_id"})) {
            statement.setString(1, comment.getCommentText());
            statement.setLong(2, comment.getNewsId());
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

    public boolean delete(Long commentId) throws DaoException {
        int countDeletedRows;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_COMMENT)) {
            statement.setLong(1, commentId);
            countDeletedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return countDeletedRows > 0;
    }

    @Override
    public boolean deleteForNews(Long newsId) throws DaoException {
        int countDeletedRows;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_COMMENTS_BY_NEWS_ID)) {
            statement.setLong(1, newsId);
            countDeletedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return countDeletedRows > 0;
    }
}
