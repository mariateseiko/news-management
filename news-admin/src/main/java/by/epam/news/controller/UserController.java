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
        return "redirect:/loginPage";
    }

    @RequestMapping(value = "/loginPage")
    public String loginUser(Model model, @RequestParam(value = "error",required = false) String error,
                            @RequestParam(value = "badcredentials",required = false) String badcredentials) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        if (badcredentials != null) {
            model.addAttribute("error", badcredentials);
        }
        return "login";
    }

}
