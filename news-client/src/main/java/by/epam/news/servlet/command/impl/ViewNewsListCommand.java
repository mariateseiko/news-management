package by.epam.news.servlet.command.impl;

import by.epam.news.domain.Author;
import by.epam.news.domain.NewsDTO;
import by.epam.news.domain.Tag;
import by.epam.news.service.*;
import by.epam.news.servlet.command.Command;
import by.epam.news.servlet.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewNewsListCommand implements Command {
    private NewsServiceFacade newsServiceFacade;
    private NewsService newsService;
    private AuthorService authorService;
    private TagService tagService;

    private static final long NEWS_LIMIT_PER_PAGE = 3;
    private static final int START_PAGE_NUMBER = 1;
    private static final Logger LOG = LogManager.getLogger(ViewNewsListCommand.class);

    public void setNewsServiceFacade(NewsServiceFacade newsServiceFacade) {
        this.newsServiceFacade = newsServiceFacade;
    }

    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String destinationPage = null;
        try {
            List<Author> authors = authorService.findNotExpiredAuthors();
            List<Tag> tags = tagService.findAll();
            long page = definePageNumber(request);
            List<NewsDTO> newsDTOList = newsServiceFacade.findAllNews(page, NEWS_LIMIT_PER_PAGE);
            if (newsDTOList != null) {
                Long totalCount = newsService.countNews();
                Long totalPageNumber = totalCount / NEWS_LIMIT_PER_PAGE;
                if (totalCount % NEWS_LIMIT_PER_PAGE != 0) {
                    totalPageNumber++;
                }
                request.setAttribute("newsDTOList", newsDTOList);
                request.setAttribute("allAuthors", authors);
                request.setAttribute("allTags", tags);
                request.setAttribute("numPages", totalPageNumber);
                request.setAttribute("page", page);
                request.setAttribute("filtered", false);

                destinationPage = "/newsList.tiles";
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (NumberFormatException e) {
            LOG.warn("Invalid page. Redirecting to 404");
        }
        return destinationPage;
    }

    private int definePageNumber(HttpServletRequest request) {
        String pageNumberParam;
        int pageNumber;
        if ((pageNumberParam = request.getParameter("page")) != null) {
            pageNumber = Integer.parseInt(pageNumberParam);
        } else {
            pageNumber = START_PAGE_NUMBER;
        }
        return pageNumber;
    }
}
