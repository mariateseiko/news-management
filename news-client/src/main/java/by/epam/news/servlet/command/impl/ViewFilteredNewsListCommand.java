package by.epam.news.servlet.command.impl;

import by.epam.news.domain.Author;
import by.epam.news.domain.NewsDTO;
import by.epam.news.domain.SearchCriteria;
import by.epam.news.domain.Tag;
import by.epam.news.service.*;
import by.epam.news.servlet.command.Command;
import by.epam.news.servlet.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewFilteredNewsListCommand implements Command {
    private NewsServiceFacade newsServiceFacade;
    private NewsService newsService;
    private AuthorService authorService;
    private TagService tagService;

    private static final Long NEWS_LIMIT_PER_PAGE = 3L;

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
        try {
            Long totalPageNumber;

            List<Author> authors = authorService.findNotExpiredAuthors();
            List<Tag> tags = tagService.findAll();

            String[] authorsId = request.getParameterValues("authors");
            String[] tagsId = request.getParameterValues("tags");
            Long page = Long.parseLong(request.getParameter("page"));
            SearchCriteria searchCriteria = formSearchCriteria(authorsId, tagsId);
            Long totalCount = newsService.countFilteredNews(searchCriteria);
            totalPageNumber = totalCount / NEWS_LIMIT_PER_PAGE;
            if (totalCount % NEWS_LIMIT_PER_PAGE != 0) {
                totalPageNumber++;
            }
            searchCriteria.setPage(page);
            searchCriteria.setLimit(NEWS_LIMIT_PER_PAGE);
            List<NewsDTO> newsDTOList = newsServiceFacade.findBySearchCriteria(searchCriteria);
            if (newsDTOList == null) {
                request.setAttribute("emptyList", "error.page");
            } else {
                request.setAttribute("newsDTOList", newsDTOList);
            }
            request.setAttribute("searchCriteria", searchCriteria);
            request.setAttribute("selectedAuthors", formSelectedAuthorMapFromArray(authors, authorsId));
            request.setAttribute("selectedTags", formSelectedTagMapFromArray(tags, tagsId));
            request.setAttribute("allAuthors", authors);
            request.setAttribute("allTags", tags);
            request.setAttribute("numPages", totalPageNumber);
            request.setAttribute("filtered", true);
            request.setAttribute("selectedNews", new ArrayList<Long>());
            return "/newsList.tiles";
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private SearchCriteria formSearchCriteria(String[] authorsId, String[] tagsId) {
        List<Author> selectedAuthors = new ArrayList<>();
        List<Tag> selectedTags = new ArrayList<>();
        if (authorsId != null) {
            for (String authorId : authorsId) {
                selectedAuthors.add(new Author(Long.parseLong(authorId)));
            }
        }
        if (tagsId != null) {
            for (String tagId: tagsId) {
                selectedTags.add(new Tag(Long.parseLong(tagId)));
            }
        }
        return new SearchCriteria(selectedTags, selectedAuthors);
    }

    private Map<Long, Boolean> formSelectedTagMapFromArray(List<Tag> allTags, String[] tagArray) {
        Map<Long, Boolean> selectedMap = new HashMap<>();
        for (Tag tag: allTags) {
            selectedMap.put(tag.getId(), false);
        }
        if (tagArray != null) {
            for (String tagId : tagArray) {
                selectedMap.put(Long.parseLong(tagId), true);
            }
        }
        return selectedMap;
    }

    private Map<Long, Boolean> formSelectedAuthorMapFromArray(List<Author> allAuthors, String[] authorArray) {
        Map<Long, Boolean> selectedMap = new HashMap<>();
        for (Author author: allAuthors) {
            selectedMap.put(author.getId(), false);
        }
        if (authorArray != null) {
            for (String authorId : authorArray) {
                selectedMap.put(Long.parseLong(authorId), true);
            }
        }
        return selectedMap;
    }
}
