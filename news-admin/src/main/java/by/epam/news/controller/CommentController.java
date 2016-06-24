package by.epam.news.controller;

import by.epam.news.domain.Comment;
import by.epam.news.service.CommentService;
import by.epam.news.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String addComment(@ModelAttribute("comment")Comment comment,
                              BindingResult result, ModelMap model) throws ServiceException {
        commentService.addComment(comment);
        return "redirect:/news/message/" + comment.getNewsId();
    }

    @RequestMapping(value="/delete/{newsId}/{commentId}", method = RequestMethod.POST)
    public String deleteComment(@PathVariable("newsId") Long newsId,
                                @PathVariable("commentId") Long commentId) throws ServiceException {
        commentService.deleteComment(commentId);
        return "redirect:/news/message/" + newsId;
    }
}
