package by.epam.news.controller;

import by.epam.news.domain.Author;
import by.epam.news.service.AuthorService;
import by.epam.news.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @RequestMapping(value = "/manage")
    public String manageAuthors(Model model) throws ServiceException {
        List<Author> authors = authorService.findNotExpiredAuthors();
        model.addAttribute("authors", authors);
        if (!model.containsAttribute("author")) {
            model.addAttribute("author", new Author());
        }
        return "authorManagement";
    }

    @RequestMapping(params="update", method = RequestMethod.POST)
    public String updateAuthor(@Valid @ModelAttribute("author") Author author,
                               BindingResult result, RedirectAttributes attr) throws ServiceException {
        if (result.hasErrors()) {
            attr.addFlashAttribute("hasErrors", true);
        } else {
            authorService.updateAuthor(author);
        }
        return "redirect:/author/manage";
    }

    @RequestMapping(params="expire", method = RequestMethod.POST)
    public String markExpired(@ModelAttribute("author") Author author) throws ServiceException {
        authorService.markExpired(author.getId());
        return "redirect:/author/manage";
    }

    @RequestMapping(params="save", method = RequestMethod.POST)
    public String addAuthor(@Valid @ModelAttribute("author") Author author,
                            BindingResult result, RedirectAttributes attr) throws ServiceException {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.author", result);
            attr.addFlashAttribute("author", author);
        } else {
            authorService.addAuthor(author);
        }
        return "redirect:/author/manage";
    }
}
