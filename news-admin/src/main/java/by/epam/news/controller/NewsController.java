package by.epam.news.controller;

import by.epam.news.domain.*;
import by.epam.news.service.AuthorService;
import by.epam.news.service.NewsService;
import by.epam.news.service.ServiceException;
import by.epam.news.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value="/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private TagService tagService;
    private static final Long newsLimitPerPage = 3L;

    @RequestMapping(value="/message/{newsId}")
    public String viewMessage(Model model, @PathVariable("newsId") Long newsId) throws ServiceException {
        NewsDTO newsDTO = newsService.findById(newsId);
        model.addAttribute("newsDTO", newsDTO);
        model.addAttribute("comment", new Comment());
        return "newsMessage";
    }

    @RequestMapping(value = "/list/{page}")
    public String viewList(Model model, @PathVariable("page") Long page) throws ServiceException {
        List<NewsDTO> newsDTOList = newsService.findAllNews(page, newsLimitPerPage);
        List<Author> authors = authorService.findNotExpiredAuthors();
        List<Tag> tags = tagService.findAll();
        model.addAttribute("newsDTOList", newsDTOList);
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        return "newsList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String viewAddNews(Model model) throws ServiceException {
        List<Author> authors = authorService.findNotExpiredAuthors();
        List<Tag> tags = tagService.findAll();
        model.addAttribute("newsDTO", new NewsDTO());
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        return "addNews";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addNews(@ModelAttribute("newsDTO") NewsDTO newsDTO,
                          BindingResult result, ModelMap model) throws ServiceException {
        Long newsId = newsService.addNews(newsDTO);
        return "redirect:/message/" + newsId;
    }
}
