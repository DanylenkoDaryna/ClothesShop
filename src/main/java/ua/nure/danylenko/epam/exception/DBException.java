package ua.nure.danylenko.epam.exception;

/**
 * The DBException class extends Exception and used for debugging and logging Exceptions caused while working with DB
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public class DBException extends AppException {

    private static final long serialVersionUID = -3550446897536410392L;

    public DBException() {
        super();
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

}
