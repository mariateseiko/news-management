package by.epam.news.controller;

import by.epam.news.domain.Tag;
import by.epam.news.service.ServiceException;
import by.epam.news.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @RequestMapping(value = "/manage")
    public String manageAuthors(Model model) throws ServiceException {
        List<Tag> tags = tagService.findAll();
        model.addAttribute("tags", tags);
        if (!model.containsAttribute("author")) {
            model.addAttribute("tag", new Tag());
        }
        return "tagManagement";
    }

    @RequestMapping(params="update", method = RequestMethod.POST)
    public String updateAuthor(@Valid @ModelAttribute("tag") Tag tag,
                               BindingResult result, RedirectAttributes attr) throws ServiceException {
        if (result.hasErrors()) {
            attr.addFlashAttribute("hasErrors", true);
        } else {
            if (!tagService.updateTag(tag)) {
                attr.addFlashAttribute("alreadyExists", "error.tag.already.exists");
            }
        }
        return "redirect:/tag/manage";
    }

    @RequestMapping(params="delete", method = RequestMethod.POST)
    public String deleteTag(@ModelAttribute("tag") Tag tag) throws ServiceException {
        tagService.deleteTag(tag.getId());
        return "redirect:/tag/manage";
    }

    @RequestMapping(params="save", method = RequestMethod.POST)
    public String addTag(@Valid @ModelAttribute("tag") Tag tag,
                         BindingResult result, RedirectAttributes attr) throws ServiceException {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.author", result);
            attr.addFlashAttribute("tag", tag);
        } else {
            if (tagService.addTag(tag) < 0) {
                attr.addFlashAttribute("alreadyExists", "error.tag.already.exists");
            }
        }
        return "redirect:/tag/manage";
    }
}
