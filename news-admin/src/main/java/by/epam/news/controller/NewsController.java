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

import java.util.List;

@Controller
@RequestMapping(value="/news")
public class NewsController {
    @Autowired
    private NewsServiceFacade newsServiceFacade;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private TagService tagService;
    private static final Long newsLimitPerPage = 3L;

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
    }

    @RequestMapping(value="/message/{newsId}")
    public String viewMessage(Model model, @PathVariable("newsId") Long newsId) throws ServiceException {
        NewsDTO newsDTO = newsServiceFacade.findById(newsId);
        model.addAttribute("newsDTO", newsDTO);
        model.addAttribute("comment", new Comment());
        return "newsMessage";
    }

    @RequestMapping(value = "/list/{page}")
    public String viewList(Model model, @PathVariable("page")Long page) throws ServiceException {
        List<NewsDTO> newsDTOList = newsServiceFacade.findAllNews(page, newsLimitPerPage);
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
        model.addAttribute("newsDTO", newsServiceFacade.findById(newsId));
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        return "addNews";
    }
}
