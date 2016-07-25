package by.epam.news.servlet.command.impl;

import by.epam.news.domain.NewsDTO;
import by.epam.news.service.NewsServiceFacade;
import by.epam.news.service.ServiceException;
import by.epam.news.servlet.command.Command;
import by.epam.news.servlet.command.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ViewNewsMessageCommand implements Command {
    private NewsServiceFacade newsServiceFacade;

    public void setNewsServiceFacade(NewsServiceFacade newsServiceFacade) {
        this.newsServiceFacade = newsServiceFacade;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            NewsDTO newsDTO = newsServiceFacade.findById(id);
            request.setAttribute("newsDTO", newsDTO);
            return "/newsMessage.tiles";
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
