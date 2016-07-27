package by.epam.news.servlet.command.impl;

import by.epam.news.servlet.command.Command;
import by.epam.news.servlet.command.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ChangeLocaleCommand implements Command {
    private static final String PARAM_LOCALE = "locale";

    /**
     * Handles request to the servlet by changing the locale for the session
     * @param request request from the servlet, containing the desired locale
     * @return origin of the request
     * @throws CommandException if exception occurred on the current or underlying level
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String locale = request.getParameter(PARAM_LOCALE);
        String localeToApply;
        switch (locale) {
            case "en":
                localeToApply = "en_US";
                break;
            case "ru":
                localeToApply = "ru_RU";
                break;
            default:
                localeToApply = "en_US";
        }
        request.getSession().setAttribute(PARAM_LOCALE, localeToApply);
        String header = request.getHeader("Referer");
        return header.substring(header.lastIndexOf('/'));
    }
}