package ua.nure.danylenko.epam.exception;

/**
 * The AppException class extends Exception and used for debugging and logging Exceptions for this app
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public class AppException extends Exception {

    private static final long serialVersionUID = 8288779062647218916L;

    public AppException() {
        super();
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }

}
