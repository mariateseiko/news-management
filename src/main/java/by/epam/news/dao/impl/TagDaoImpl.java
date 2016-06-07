package by.epam.news.dao.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.TagDao;
import by.epam.news.domain.Tag;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDaoImpl implements TagDao {
    private DataSource dataSource;

    private static final String SELECT_TAG_BY_ID = "SELECT tag_id, tag_name FROM tags WHERE tag_id = ?";
    private static final String UPDATE_TAG = "UPDATE tags SET tag_name = ? WHERE tag_id = ?";
    private static final String SELECT_ALL_TAGS = "SELECT tag_id, tag_name FROM tags";
    private static final String INSERT_TAG = "INSERT INTO tags (tag_name) VALUES(?)";
    private static final String SELECT_FOR_NEWS = "SELECT tags.tag_id, tags.tag_name FROM tags " +
            "JOIN news_tags ON tags.tag_id = news_tags.tag_id " +
            "WHERE news_id = ?";


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Tag selectById(Long id) throws DaoException {
        Tag tag = null;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SELECT_TAG_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tag = new Tag();
                tag.setId(resultSet.getLong("tag_id"));
                tag.setName(resultSet.getString("tag_name"));
            }
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return tag;
    }

    @Override
    public boolean update(Tag tag) throws DaoException {
        int countUpdatedRows;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_TAG)) {
            statement.setString(1, tag.getName());
            statement.setLong(2, tag.getId());
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
    public List<Tag> selectAll() throws DaoException {
        List<Tag> tags = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TAGS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setId(resultSet.getLong("tag_id"));
                tag.setName(resultSet.getString("tag_name"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
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
        try (PreparedStatement statement = connection.prepareStatement(SELECT_FOR_NEWS)) {
            statement.setLong(1, newsId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setId(resultSet.getLong("tag_id"));
                tag.setName(resultSet.getString("tag_name"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return tags;    }

    @Override
    public Long insert(Tag tag) throws DaoException {
        Long generatedId = -1L;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(INSERT_TAG, new String[]{"tag_id"})) {
            statement.setString(1, tag.getName());
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
}
