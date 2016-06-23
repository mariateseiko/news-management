package by.epam.news.dao.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.NewsDao;
import by.epam.news.dao.utl.SQLQueryBuilder;
import by.epam.news.domain.News;
import by.epam.news.domain.SearchCriteria;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl implements NewsDao {
    private DataSource dataSource;
    private static final String SELECT_TOTAL_COUNT = "SELECT COUNT(news_id) AS total FROM news";
    private static final String SELECT_NEWS_BY_AUTHOR = "SELECT news.news_id, news.title, news.short_text, " +
            "news.full_text, news.creation_date, " +
            "modification_date FROM news " +
            "JOIN news_authors ON news.news_id = news_authors.news_id " +
            "LEFT JOIN comments ON news.news_id = comments.news_id " +
            "WHERE author_id = ? " +
            "GROUP BY news.news_id, news.title, news.short_text, news.full_text, " +
            "news.creation_date, news.modification_date " +
            "ORDER BY COUNT(comment_id) DESC";
    private static final String SELECT_ALL_NEWS = "SELECT *  FROM (" +
            "SELECT news.news_id, news.title, news.short_text, " +
            "news.creation_date, modification_date, ROW_NUMBER() OVER (ORDER BY COUNT(comment_id) DESC) " +
            "AS rn FROM news " +
            "LEFT JOIN comments ON news.news_id = comments.news_id " +
            "GROUP BY news.news_id, news.title, news.short_text, news.full_text, " +
            "news.creation_date, news.modification_date) WHERE rn BETWEEN ? AND ?";
    private static final String SELECT_NEWS_BY_ID = "SELECT news_id, title, short_text, full_text, creation_date, " +
            "modification_date FROM news WHERE news_id=?";
    private static final String UPDATE_NEWS = "UPDATE news SET title=?, short_text=?, full_text=?, " +
            "modification_date = CURRENT_TIMESTAMP WHERE news_id=?";
    private static final String INSERT_NEWS = "INSERT INTO news (title, short_text, full_text, creation_date, " +
            "modification_date) VALUES (?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
    private static final String LINK_TAG_NEWS = "INSERT INTO news_tags (news_id, tag_id) VALUES(?,?)";
    private static final String UNLINK_TAG_NEWS = "DELETE FROM news_tags WHERE news_id=? AND tag_id=?";
    private static final String LINK_AUTHOR_NEWS = "INSERT INTO news_authors (news_id, author_id) VALUES(?,?)";
    private static final String DELETE_NEWS = "DELETE FROM news WHERE news_id=?";


    private static final String NEWS_ID = "news_id";
    private static final String NEWS_TITLE = "title";
    private static final String SHORT_TEXT = "short_text";
    private static final String CREATION_DATE = "creation_date";
    private static final String MODIFICATION_DATE = "modification_date";

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Integer selectTotalCount() throws DaoException {
        Integer totalCount = 0;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SELECT_TOTAL_COUNT)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalCount = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        }
        return totalCount;
    }

    @Override
    public List<News> selectNewsByAuthor(Long authorId) {
        List<News> newsList = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SELECT_NEWS_BY_AUTHOR)) {
            statement.setLong(1, authorId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                newsList.add(extractNewsMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }

        @Override
    public News selectById(Long id) throws DaoException {
            News news = null;
            Connection connection = DataSourceUtils.getConnection(dataSource);
            try (PreparedStatement statement = connection.prepareStatement(SELECT_NEWS_BY_ID)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    news = extractNewsMessageFromResultSet(resultSet);
                    news.setFullText(resultSet.getString("full_text"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return news;
    }

    @Override
    public boolean update(News news) throws DaoException {
        int countUpdatedRows;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_NEWS)) {
            statement.setString(1, news.getTitle());
            statement.setString(2, news.getShortText());
            statement.setString(3, news.getFullText());
            statement.setLong(4, news.getId());
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
    public Long insert(News news) throws DaoException {
        Long generatedId = -1L;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEWS, new String[]{NEWS_ID})) {
            statement.setString(1, news.getTitle());
            statement.setString(2, news.getShortText());
            statement.setString(3, news.getFullText());
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
    public boolean linkTagNews(Long newsId, Long tagId) throws DaoException {
        int countInsertedRows;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(LINK_TAG_NEWS)) {
            statement.setLong(1, newsId);
            statement.setLong(2, tagId);
            countInsertedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return countInsertedRows > 0;
    }

    @Override
    public boolean unlinkTagNews(Long newsId, Long tagId) throws DaoException {
        int countDeletedRows;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(UNLINK_TAG_NEWS)) {
            statement.setLong(1, newsId);
            statement.setLong(2, tagId);
            countDeletedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return countDeletedRows > 0;    }

    @Override
    public boolean linkAuthorNews(Long newsId, Long authorId) throws DaoException {
        int countInsertedRows;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(LINK_AUTHOR_NEWS)) {
            statement.setLong(1, newsId);
            statement.setLong(2, authorId);
            countInsertedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return countInsertedRows > 0;
    }

    public boolean delete(Long newsId) throws DaoException {
        int countDeletedRows;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_NEWS)) {
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

    @Override
    public List<News> selectAllNews(Long page, Long limit) throws DaoException {
        List<News> newsList = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_NEWS)) {
            statement.setLong(2, page*limit);
            statement.setLong(1, (page-1)*limit + 1);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                newsList.add(extractNewsMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        }
        return newsList;
    }

    @Override
    public List<News> selectBySearchCriteria(SearchCriteria searchCriteria) throws DaoException {
        String searchQuery = SQLQueryBuilder.buildSearchQuery(searchCriteria);
        List<News> newsList = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(searchQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                newsList.add(extractNewsMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        }
        return newsList;
    }

    private News extractNewsMessageFromResultSet(ResultSet resultSet) throws SQLException {
        News news = new News();
        news.setId(resultSet.getLong(NEWS_ID));
        news.setTitle(resultSet.getString(NEWS_TITLE));
        news.setShortText(resultSet.getString(SHORT_TEXT));
        news.setCreationDate(resultSet.getTimestamp(CREATION_DATE));
        news.setModificationDate(resultSet.getTimestamp(MODIFICATION_DATE));
        return news;
    }
}
