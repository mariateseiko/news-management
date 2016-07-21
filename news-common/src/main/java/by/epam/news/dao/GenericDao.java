package by.epam.news.dao;

/**
 * Represents a generic interface for CRUD operations
 * @param <K> key
 * @param <T> value
 */
public interface GenericDao<K, T> {
    T selectById(K id) throws DaoException;
    Long insert(T entity) throws DaoException;
}
