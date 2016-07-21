package by.epam.news.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(by.epam.news.service.ServiceException.class)
    public ModelAndView handleServiceException(by.epam.news.service.ServiceException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorCode", 500);
        modelAndView.addObject("errorMessage", "error.message.500");
        modelAndView.addObject("exception", e);
        return modelAndView;
    }
}
