package ua.nure.danylenko.epam.web.command;

import ua.nure.danylenko.epam.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * The Command abstract class implements Serializable and provides execution method for command
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public abstract class Command implements Serializable {
    private static final long serialVersionUID = 8879403039606311780L;

    /**
     * Execution method for command.
     *
     * @return Address to go once the command is executed.
     */
    public abstract String execute(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException, ServletException,
            AppException;

    @Override
    public final String toString() {
        return getClass().getSimpleName();
    }
}