package by.epam.news.controller;

import by.epam.news.domain.NewsDTO;
import by.epam.news.service.NewsService;
import by.epam.news.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @RequestMapping(value="/view/message/{newsId}")
    public String viewMessage(Model model, @PathVariable("newsId") Long newsId) throws ServiceException {
        NewsDTO newsDTO = newsService.findById(newsId);
        model.addAttribute("newsDTO", newsDTO);
        return "newsMessage";
    }
}
