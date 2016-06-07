package by.epam.news.service;

/**
 * Represents a checked exception that may be thrown anywhere on the service layer
 */
public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
