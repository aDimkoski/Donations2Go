package com.donations2go.donations2go.web;

import com.donations2go.donations2go.service.FoodBankService;
import com.donations2go.donations2go.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/foodbanks")
public class FoodbankController {

    private final FoodBankService foodBankService;

    private final UserService userService;

    public FoodbankController(FoodBankService foodBankService, UserService userService) {
        this.foodBankService = foodBankService;
        this.userService = userService;
    }

    @GetMapping("/addpage")
    public String addPage(Model model){
        if(userService.isAuthenticated()) {
            model.addAttribute("isAuthenticated", true);
        }
        else {
            model.addAttribute("isAuthenticated", false);
        }
        return "addFoodbank";
    }

    @PostMapping("/add")
    public String addFoodbank(@RequestParam String name,
                             @RequestParam String num){
        this.foodBankService.save(name,num);
        return "redirect:/auth/profile";
    }
}
