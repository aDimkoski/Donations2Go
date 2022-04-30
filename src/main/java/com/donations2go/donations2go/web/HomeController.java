package com.donations2go.donations2go.web;

import com.donations2go.donations2go.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String home(Model model){
        if(userService.isAuthenticated()) {
            model.addAttribute("isAuthenticated", true);
        }
        else {
            model.addAttribute("isAuthenticated", false);
        }
        return "home";
    }
}
