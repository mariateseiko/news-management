package by.epam.news.dao.impl;

import by.epam.news.dao.DaoException;
import by.epam.news.dao.NewsDao;
import by.epam.news.dao.util.SQLQueryBuilder;
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
    private static final String SQL_SELECT_TOTAL_COUNT = "SELECT COUNT(news.news_id) AS total FROM news";
    private static final String SQL_SELECT_FILTERED_COUNT = "SELECT COUNT(news_id) AS total FROM (";

    private static final String SQL_SELECT_NEWS_BY_AUTHOR = "SELECT news.news_id, news.title, news.short_text, " +
            "news.full_text, news.creation_date, " +
            "modification_date FROM news " +
            "JOIN news_authors ON news.news_id = news_authors.news_id " +
            "LEFT JOIN comments ON news.news_id = comments.news_id " +
            "WHERE author_id = ? " +
            "GROUP BY news.news_id, news.title, news.short_text, news.full_text, " +
            "news.creation_date, news.modification_date " +
            "ORDER BY COUNT(comment_id) DESC";
    private static final String SQL_SELECT_ALL_NEWS = "SELECT *  FROM (" +
            "SELECT news.news_id, news.title, news.short_text, " +
            "news.creation_date, modification_date, ROW_NUMBER() OVER " +
            "(ORDER BY COUNT(comment_id) DESC, news.modification_date DESC) " +
            "AS rn FROM news " +
            "LEFT JOIN comments ON news.news_id = comments.news_id " +
            "GROUP BY news.news_id, news.title, news.short_text, news.full_text, " +
            "news.creation_date, news.modification_date) WHERE rn BETWEEN ? AND ?";
    private static final String SQL_SELECT_NEWS_BY_ID = "SELECT news_id, title, short_text, " +
            "full_text, creation_date, modification_date, previd, nextid " +
            "FROM (SELECT news.news_id, news.title, news.short_text, news.full_text, " +
            "news.creation_date, news.modification_date, " +
            "lead(news.news_id,1,0) over (order by count(comment_id) DESC, news.modification_date DESC) as previd, " +
            "lag(news.news_id,1,0) over (order by count(comment_id) DESC, news.modification_date DESC) as nextid " +
            "FROM news LEFT JOIN comments ON news.news_id = comments.news_id " +
            "GROUP BY news.news_id, title, short_text, full_text, news.creation_date, modification_date) WHERE news_id = ?";
    private static final String SQL_UPDATE_NEWS = "UPDATE news SET title=?, short_text=?, full_text=?, " +
            "modification_date = CURRENT_TIMESTAMP WHERE news_id=?";
    private static final String SQL_INSERT_NEWS = "INSERT INTO news (title, short_text, full_text, creation_date, " +
            "modification_date) VALUES (?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
    private static final String SQL_DELETE_NEWS = "DELETE FROM news WHERE news_id=?";

    private static final String NEWS_ID = "news_id";
    private static final String NEWS_TITLE = "title";
    private static final String SHORT_TEXT = "short_text";
    private static final String CREATION_DATE = "creation_date";
    private static final String MODIFICATION_DATE = "modification_date";

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long selectTotalCount() throws DaoException {
        Long totalCount = 0L;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TOTAL_COUNT)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalCount = resultSet.getLong("total");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to detect total count of all news", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return totalCount;
    }

    @Override
    public Long selectFilteredCount(SearchCriteria searchCriteria) throws DaoException {
        Long totalCount = 0L;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        String fullStatement = SQL_SELECT_FILTERED_COUNT + SQLQueryBuilder.buildSearchQuery(searchCriteria) + ")";
        try (PreparedStatement statement = connection.prepareStatement(fullStatement)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalCount = resultSet.getLong("total");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to detect total count of filtered news", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return totalCount;
    }

    @Override
    public List<News> selectNewsByAuthor(Long authorId) throws DaoException{
        List<News> newsList = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_NEWS_BY_AUTHOR)) {
            statement.setLong(1, authorId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                newsList.add(extractNewsMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select all news for author with newsId: " + authorId, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return newsList;
    }

    @Override
    public News selectById(Long newsId) throws DaoException {
            News news = null;
            Connection connection = DataSourceUtils.getConnection(dataSource);
            try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_NEWS_BY_ID)) {
                statement.setLong(1, newsId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    news = extractNewsMessageFromResultSet(resultSet);
                    news.setFullText(resultSet.getString("full_text"));
                    news.setNextId(resultSet.getLong("nextid"));
                    news.setPreviousId(resultSet.getLong("previd"));
                }
            } catch (SQLException e) {
                throw new DaoException("Failed to select news by newsId: " + newsId, e);
            } finally {
                if (connection != null) {
                    DataSourceUtils.releaseConnection(connection, dataSource);
                }
            }
            return news;
    }

    @Override
    public void update(News news) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_NEWS)) {
            statement.setString(1, news.getTitle());
            statement.setString(2, news.getShortText());
            statement.setString(3, news.getFullText());
            statement.setLong(4, news.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update news: " + news, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    @Override
    public Long insert(News news) throws DaoException {
        Long generatedId = -1L;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEWS, new String[]{NEWS_ID})) {
            statement.setString(1, news.getTitle());
            statement.setString(2, news.getShortText());
            statement.setString(3, news.getFullText());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to insert news: " + news, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
        return generatedId;
    }



    public void delete(Long newsId) throws DaoException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_NEWS)) {
            statement.setLong(1, newsId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Request to database failed", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    @Override
    public List<News> selectAllNews(Long page, Long limit) throws DaoException {
        List<News> newsList = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_NEWS)) {
            statement.setLong(2, page*limit);
            statement.setLong(1, (page-1)*limit + 1);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                newsList.add(extractNewsMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to select all news for page: " + page + " with limit " + limit, e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
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
            throw new DaoException("Failed to select news by search criteria", e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
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
