package by.epam.news.servlet.command.impl;

import by.epam.news.domain.Comment;
import by.epam.news.service.CommentService;
import by.epam.news.service.ServiceException;
import by.epam.news.servlet.command.Command;
import by.epam.news.servlet.command.CommandException;
import by.epam.news.servlet.validator.Validator;

import javax.servlet.http.HttpServletRequest;

public class PostCommentCommand implements Command {
    private CommentService commentService;

    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Long newsId = Long.parseLong(request.getParameter("newsId"));
        String commentText = request.getParameter("text");
        try {
            if (Validator.validateComment(commentText)) {
                commentService.addComment(new Comment(newsId, commentText));
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "/controller?command=view_news&id=" + newsId;
    }
}
