package by.epam.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "redirect:/news/list/1";
    }

    @RequestMapping(value = "/loginPage")
    public String loginUser(Model model, @RequestParam(value = "error",required = false) String error,
                            @RequestParam(value = "badcredentials",required = false) String badcredentials) {
        if (error != null) {
            model.addAttribute("errorMessage", "invalid.login.password");
        }
        return "login";
    }

    @RequestMapping(value = "/error")
    public String accessDenied(Model model, @RequestParam(value = "code") Integer code) {
        model.addAttribute("errorCode", code);
        model.addAttribute("errorMessage", "error."+code);
        return "error";
    }
}
