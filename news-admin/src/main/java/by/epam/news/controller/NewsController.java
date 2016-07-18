package by.epam.news.controller;

import by.epam.news.domain.*;
import by.epam.news.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/news")
public class NewsController {
    @Autowired
    private NewsServiceFacade newsServiceFacade;
    @Autowired
    private NewsService newsService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private TagService tagService;
    private static final Long newsLimitPerPage = 3L;
    private static final Long defaultPage = 1L;

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, "tags", new CustomCollectionEditor(List.class) {
            protected Object convertElement(Object element) {
                if (element != null) {
                    Long id = new Long((String)element);
                    Tag tag = new Tag();
                    tag.setId(id);
                    return tag;
                }
                return null;
            }
        });
        binder.registerCustomEditor(List.class, "authors", new CustomCollectionEditor(List.class) {
            protected Object convertElement(Object element) {
                if (element != null) {
                    Long id = new Long((String)element);
                    return new Author(id);
                }
                return null;
            }
        });
    }

    @RequestMapping(value="/message/{newsId}")
    public String viewMessage(Model model, @PathVariable("newsId") Long newsId) throws ServiceException {
        NewsDTO newsDTO = newsServiceFacade.findById(newsId);
        if (newsDTO == null) {
            return "redirect:/error?code=404";
        }
        model.addAttribute("newsDTO", newsDTO);
        model.addAttribute("comment", new Comment());

        return "newsMessage";
    }

    @RequestMapping(value = "/list/{page}")
    public String viewList(Model model, @PathVariable("page")Long page) throws ServiceException {
        List<Author> authors = authorService.findNotExpiredAuthors();
        List<Tag> tags = tagService.findAll();
        List<NewsDTO> newsDTOList = newsServiceFacade.findAllNews(page, newsLimitPerPage);
        if (newsDTOList == null) {
            model.addAttribute("emptyList", "error.page");
        }
        Long totalCount = newsService.countNews();
        Long totalPageNumber = totalCount / newsLimitPerPage;
        if (totalCount % newsLimitPerPage != 0) {
            totalPageNumber++;
        }
        model.addAttribute("searchCriteria", new SearchCriteria());
        model.addAttribute("newsDTOList", newsDTOList);
        model.addAttribute("allAuthors", authors);
        model.addAttribute("allTags", tags);
        model.addAttribute("numPages", totalPageNumber);
        model.addAttribute("page", page);
        model.addAttribute("selectedNews", new ArrayList<Long>());
        return "newsList";
    }

    @RequestMapping(value = "/list")
    public String viewList(Model model) throws ServiceException {
        return viewList(model, defaultPage);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String viewAddNews(Model model) throws ServiceException {
        List<Author> authors = authorService.findNotExpiredAuthors();
        List<Tag> tags = tagService.findAll();
        model.addAttribute("newsDTO", new NewsDTO());
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("selectedNews", new ArrayList<Long>());
        return "addNews";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String addNews(@ModelAttribute("newsDTO") NewsDTO newsDTO,
                          BindingResult result, ModelMap model) throws ServiceException {
        Long newsId = newsServiceFacade.saveNews(newsDTO);
        return "redirect:/news/message/" + newsId;
    }

    @RequestMapping(value = "/edit/{newsId}", method = RequestMethod.GET)
    public String viewEditNews(Model model, @PathVariable("newsId") Long newsId) throws ServiceException {
        List<Author> authors = authorService.findNotExpiredAuthors();
        List<Tag> tags = tagService.findAll();
        NewsDTO newsDTO = newsServiceFacade.findById(newsId);
        if (newsDTO != null) {
            model.addAttribute("newsDTO", newsDTO);
            model.addAttribute("authors", authors);
            model.addAttribute("tags", tags);
            return "addNews";
        } else {
            return "redirect:/error?code=404";
        }

    }

    @RequestMapping(value="/filter", params = "page" , method = RequestMethod.GET)
    public String viewFilteredNewsPages(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria,
                                       @RequestParam(value = "page") Long page,
                                       BindingResult result, ModelMap model) throws ServiceException {
        Long totalPageNumber;

        List<Author> authors = authorService.findNotExpiredAuthors();
        List<Tag> tags = tagService.findAll();
        Long totalCount = newsService.countFilteredNews(searchCriteria);
        totalPageNumber = totalCount / newsLimitPerPage;
        if (totalCount % newsLimitPerPage != 0) {
            totalPageNumber++;
        }
        searchCriteria.setPage(page);
        searchCriteria.setLimit(newsLimitPerPage);
        List<NewsDTO> newsDTOList = newsServiceFacade.findBySearchCriteria(searchCriteria);
        if (newsDTOList == null) {
            model.addAttribute("emptyList", "error.page");
        }
        model.addAttribute("newsDTOList", newsDTOList);
        model.addAttribute("searchCriteria", searchCriteria);
        model.addAttribute("allAuthors", authors);
        model.addAttribute("allTags", tags);
        model.addAttribute("numPages", totalPageNumber);
        model.addAttribute("filtered", true);
        model.addAttribute("selectedNews", new ArrayList<Long>());
        return "newsList";
    }


    @RequestMapping(value = "/filter", params = "reset", method = RequestMethod.GET)
    public String resetFilteredNews(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria,
                                   BindingResult result, ModelMap model) throws ServiceException {
        return "redirect:/news/list/1";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteMultipleNews(@RequestParam(value = "selectedNews", required=false) Long[] selectedNews,
                                     BindingResult result, ModelMap model) throws ServiceException {
        if (selectedNews.length != 0) {
            for (Long id: selectedNews) {
                newsService.deleteNews(id);
            }
        }
        return "redirect:/news/list/1";
    }
}
