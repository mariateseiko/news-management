package by.epam.news.dao.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.TagDao;
import by.epam.news.domain.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagDaoImpl implements TagDao {
    private DataSource dataSource;

    private static final String SQL_SELECT_TAG_BY_ID = "SELECT tag_id, tag_name FROM tags WHERE tag_id = ?";
    private static final String SQL_UPDATE_TAG = "UPDATE tags SET tag_name = ? WHERE tag_id = ?";
    private static final String SQL_SELECT_ALL_TAGS = "SELECT tag_id, tag_name FROM tags";
    private static final String SQL_INSERT_TAG = "INSERT INTO tags (tag_name) VALUES(?)";
    private static final String SQL_SELECT_FOR_NEWS = "SELECT tags.tag_id, tags.tag_name FROM tags " +
            "JOIN news_tags ON tags.tag_id = news_tags.tag_id " +
            "WHERE news_id = ?";
    private static final String SQL_DELETE_TAG = "DELETE FROM tags WHERE tag_id = ?";
    private static final String SQL_LINK_TAG_NEWS = "INSERT INTO news_tags (news_id, tag_id) VALUES(?,?)";
    private static final String SQL_UNLINK_TAGS_NEWS = "DELETE FROM news_tags WHERE news_id=?";

    private static final String TAG_ID = "tag_id";
    private static final String TAG_NAME = "tag_name";
    private static final Logger LOG = LogManager.getLogger(TagDaoImpl.class);

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Tag selectById(Long tagId) throws DaoException {
        Tag tag = null;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TAG_BY_ID)) {
            statement.setLong(1, tagId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tag = extractTagFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select tag with tagId: " + tagId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return tag;
    }

    @Override
    public Boolean update(Tag tag) throws DaoException {
        Boolean result = true;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_TAG)) {
            statement.setString(1, tag.getName());
            statement.setLong(2, tag.getId());
            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            result = false;
            LOG.warn("Tag with name : " + tag.getName() + " already exists. Insert failed.");
        } catch (SQLException e) {
            throw new DaoException("Failed to update tag: " + tag, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return result;
    }

    @Override
    public List<Tag> selectAll() throws DaoException {
        List<Tag> tags = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_TAGS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tag tag = extractTagFromResultSet(resultSet);
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select all tags", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return tags;
    }

    @Override
    public List<Tag> selectForNews(Long newsId) throws DaoException {
        List<Tag> tags = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FOR_NEWS)) {
            statement.setLong(1, newsId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tag tag = extractTagFromResultSet(resultSet);
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select all tags for news with id: " + newsId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return tags;
    }

    @Override
    public void delete(Long tagId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TAG)) {
            statement.setLong(1, tagId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to delete tag with id: " + tagId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    @Override
    public Long insert(Tag tag) throws DaoException {
        Long generatedId = -1L;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TAG, new String[]{TAG_ID})) {
            statement.setString(1, tag.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getLong(1);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            LOG.warn("Tag with name : " + tag.getName() + " already exists. Insert failed.");
        } catch (SQLException e) {
            throw new DaoException("Failed to insert tag: " + tag, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return generatedId;
    }

    @Override
    public void linkTagNews(Long newsId, Long tagId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_LINK_TAG_NEWS)) {
            statement.setLong(1, newsId);
            statement.setLong(2, tagId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to link news with id: " + newsId + " and tag with id " + tagId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    @Override
    public void unlinkAllTags(Long newsId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_UNLINK_TAGS_NEWS)) {
            statement.setLong(1, newsId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to unlink all tags from news with id: " + newsId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    private Tag extractTagFromResultSet(ResultSet resultSet) throws SQLException {
        Tag tag = new Tag();
        tag.setId(resultSet.getLong(TAG_ID));
        tag.setName(resultSet.getString(TAG_NAME));
        return tag;
    }
}
