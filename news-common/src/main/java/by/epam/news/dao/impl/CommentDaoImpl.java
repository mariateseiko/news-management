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
    private static final String SQL_SELECT_COMMENT_BY_ID = "SELECT comment_id, comment_text, " +
            "creation_date, news_id FROM comments WHERE comment_id=?";
    private static final String SQL_UPDATE_COMMENT = "UPDATE comments SET comment_text=? WHERE comment_id = ?";
    private static final String SQL_SELECT_ALL_COMMENTS_BY_NEWS = "SELECT comment_id, comment_text, creation_date, news_id " +
            "FROM comments WHERE news_id=?";
    private static final String SQL_INSERT_COMMENT = "INSERT INTO comments (comment_text, news_id, creation_date) " +
            "VALUES(?, ?, CURRENT_TIMESTAMP)";
    private static final String SQL_DELETE_COMMENT = "DELETE FROM comments WHERE comment_id=?";
    private static final String SQL_DELETE_COMMENTS_BY_NEWS_ID = "DELETE FROM comments WHERE news_id=?";
    private static final String SQL_SELECT_NEWS_COMMENTS_COUNT = "SELECT COUNT(comment_id) AS commentCount FROM comments GROUP BY news_id HAVING news_id=?";

    private static final String NEWS_ID = "news_id";
    private static final String COMMENT_ID = "comment_id";
    private static final String COMMENT_TEXT = "comment_text";
    private static final String CREATION_DATE = "creation_date";

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Comment selectById(Long commentId) throws DaoException {
        Comment comment = null;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COMMENT_BY_ID)) {
            statement.setLong(1, commentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                comment = extractCommentFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select comment with commentId: " + commentId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return comment;
    }

    @Override
    public void update(Comment comment) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_COMMENT)) {
            statement.setString(1, comment.getCommentText());
            statement.setLong(2, comment.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update comment: " + comment, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    public List<Comment> selectForNews(Long newsId) throws DaoException {
        List<Comment> comments = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_COMMENTS_BY_NEWS)) {
            statement.setLong(1, newsId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Comment comment = extractCommentFromResultSet(resultSet);
                comments.add(comment);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select comments for news with id: " + newsId, e);
        }
        return comments;
    }

    @Override
    public Long insert(Comment comment) throws DaoException {
        Long generatedId = -1L;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_COMMENT, new String[]{COMMENT_ID})) {
            statement.setString(1, comment.getCommentText());
            statement.setLong(2, comment.getNewsId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to insert comment: " + comment, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return generatedId;
    }

    public void delete(Long commentId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_COMMENT)) {
            statement.setLong(1, commentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to delete comment with id: " + commentId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    @Override
    public void deleteForNews(Long newsId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_COMMENTS_BY_NEWS_ID)) {
            statement.setLong(1, newsId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to delete all comments for news with id: " + newsId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    @Override
    public Integer selectNewsCommentsCount(Long newsId) throws DaoException {
        Integer count = 0;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_NEWS_COMMENTS_COUNT)) {
            statement.setLong(1, newsId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("commentCount");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select comments for news with id: " + newsId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return count;
    }

    private Comment extractCommentFromResultSet(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getLong(COMMENT_ID));
        comment.setCommentText(resultSet.getString(COMMENT_TEXT));
        comment.setCreationDate(resultSet.getTimestamp(CREATION_DATE));
        comment.setNewsId(resultSet.getLong(NEWS_ID));
        return comment;
    }
}
