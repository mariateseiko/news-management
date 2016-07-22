package by.epam.news.servlet.command.impl;

import by.epam.news.service.AuthorService;
import by.epam.news.service.NewsService;
import by.epam.news.service.NewsServiceFacade;
import by.epam.news.service.TagService;
import by.epam.news.servlet.command.Command;
import by.epam.news.servlet.command.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ViewFilteredNewsListCommand implements Command {
    private NewsServiceFacade newsServiceFacade;
    private NewsService newsService;
    private AuthorService authorService;
    private TagService tagService;

    public void setNewsServiceFacade(NewsServiceFacade newsServiceFacade) {
        this.newsServiceFacade = newsServiceFacade;
    }

    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
