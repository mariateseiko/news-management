package by.epam.news.servlet.command.impl;

import by.epam.news.service.NewsService;
import by.epam.news.service.NewsServiceFacade;
import by.epam.news.servlet.command.Command;
import by.epam.news.servlet.command.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ViewNewsMessageCommand implements Command {
    private NewsServiceFacade newsServiceFacade;
    private NewsService newsService;

    public void setNewsServiceFacade(NewsServiceFacade newsServiceFacade) {
        this.newsServiceFacade = newsServiceFacade;
    }

    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
