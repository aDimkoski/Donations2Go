package com.donations2go.donations2go.web;

import com.donations2go.donations2go.model.enums.Role;
import com.donations2go.donations2go.service.impl.UserService;
import com.nimbusds.oauth2.sdk.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class PostRegisterController {
    private final UserService userService;

    public PostRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/postregister")
    public String getpostRegisterPage(){
        return "postRegister";
    }

    @PostMapping("/finish")
    public String saveUser(@RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String phone,
                           @RequestParam String address,
                           @RequestParam String country,
                           @RequestParam String city,
                           @RequestParam String picture,
                           @RequestParam String job,
                           @RequestParam String organization) {
        if(Objects.equals(organization, "restaurant")){
            this.userService.updateUser(name,surname,phone,address,country,city,picture,job, Role.ROLE_RESTAURANT);
        }
        else {
            this.userService.updateUser(name,surname,phone,address,country,city,picture,job,Role.ROLE_FOODBANK);
        }
        return "redirect:/auth/profile";
    }

}
