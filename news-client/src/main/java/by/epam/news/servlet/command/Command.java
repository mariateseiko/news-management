package by.epam.news.servlet.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Class {@code Command} is an interface representing a command, handled by a servlet
 */
public interface Command {
    /**
     * Performs necessary actions, retrieves information from the service layers and sets
     * it to the request if necessary.
     * @param request request from the servlet
     * @return path to the page to forward request to
     * @throws CommandException
     */
    String execute(HttpServletRequest request) throws CommandException;
}