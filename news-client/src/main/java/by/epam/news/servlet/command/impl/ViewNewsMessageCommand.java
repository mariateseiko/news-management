package by.epam.news.servlet.command.impl;

import by.epam.news.domain.NewsDTO;
import by.epam.news.service.NewsServiceFacade;
import by.epam.news.service.ServiceException;
import by.epam.news.servlet.command.Command;
import by.epam.news.servlet.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;

public class ViewNewsMessageCommand implements Command {
    private NewsServiceFacade newsServiceFacade;
    private static final Logger LOG = LogManager.getLogger(ViewNewsMessageCommand.class);

    public void setNewsServiceFacade(NewsServiceFacade newsServiceFacade) {
        this.newsServiceFacade = newsServiceFacade;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String destinationPage = null;
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            NewsDTO newsDTO = newsServiceFacade.findById(id);
            if (newsDTO != null) {
                request.setAttribute("newsDTO", newsDTO);
                destinationPage = "/newsMessage.tiles";
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (NumberFormatException e) {
            LOG.warn("Invalid news id. Redirecting to 404");
        }
        return destinationPage;
    }
}
