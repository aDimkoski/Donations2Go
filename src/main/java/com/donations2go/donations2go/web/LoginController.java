package com.donations2go.donations2go.web;

import com.donations2go.donations2go.model.User;
import com.donations2go.donations2go.model.enums.Role;
import com.donations2go.donations2go.service.RestaurantService;
import com.donations2go.donations2go.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {

    private final UserService userService;

    private final RestaurantService restaurantService;
    public LoginController(UserService userService, RestaurantService restaurantService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public String getLoginPage(Model model){
        if(userService.isAuthenticated()) {
            model.addAttribute("isAuthenticated", true);
        }
        else {
            model.addAttribute("isAuthenticated", false);
        }
        return "login";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model){
        if(userService.isAuthenticated()) {
            model.addAttribute("isAuthenticated", true);
        }
        else {
            model.addAttribute("isAuthenticated", false);
        }
        User user=this.userService.findUserByMail().get();
        if(user.getRole().equals(Role.ROLE_RESTAURANT) && user.getRestaurant()==null) {
            model.addAttribute("isRestaurant", true);
            model.addAttribute("restaurantAdded", false);
        }
        if(user.getRole().equals(Role.ROLE_FOODBANK) && user.getFoodbank()==null) {
            model.addAttribute("isFoodbank", true);
            model.addAttribute("foodbankAdded",false);
        }
        if(user.getRole().equals(Role.ROLE_RESTAURANT) && user.getRestaurant() != null){
            model.addAttribute("isRestaurant", true);
            model.addAttribute("restaurantAdded", true);
        }
        if(user.getRole().equals(Role.ROLE_FOODBANK) && user.getFoodbank() != null){
            model.addAttribute("isFoodbank",true);
            model.addAttribute("foodbankAdded",true);
        }
        if(user.getRole().equals(Role.ROLE_FOODBANK) && user.getFoodbank() != null && user.getFoodList().size()>0){
            model.addAttribute("myListings",true);
            model.addAttribute("foods",user.getFoodList());
        }
        model.addAttribute("currentUser",user);
        return "profilePage";
    }
}
