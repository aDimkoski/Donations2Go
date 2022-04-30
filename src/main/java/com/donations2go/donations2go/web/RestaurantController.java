package com.donations2go.donations2go.web;

import com.donations2go.donations2go.model.Food;
import com.donations2go.donations2go.model.User;
import com.donations2go.donations2go.model.enums.Country;
import com.donations2go.donations2go.model.enums.Role;
import com.donations2go.donations2go.model.enums.TypeOfFood;
import com.donations2go.donations2go.model.exceptions.FoodNotFoundException;
import com.donations2go.donations2go.service.FoodService;
import com.donations2go.donations2go.service.RestaurantService;
import com.donations2go.donations2go.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final FoodService foodService;

    private final UserService userService;


    public RestaurantController(RestaurantService restaurantService, FoodService foodService, UserService userService) {
        this.restaurantService = restaurantService;
        this.foodService = foodService;
        this.userService = userService;
    }

    @GetMapping("/listings")
    public String getListingsPage(Model model) {
        List<User> list = this.userService.findAllByRole();
        model.addAttribute("users",list);
        User user = this.userService.findUserByMail().get();
        if(user.getRole().equals(Role.ROLE_RESTAURANT)){
            model.addAttribute("isRestaurant", true);
        }
        return "listings";
    }

    @GetMapping("/listings/addPage")
    public String getAddListingsPage(Model model) {
        model.addAttribute("foods", this.userService.findUserByMail().get().getRestaurant().getFoodList());
        return "addListings";
    }

    @PostMapping("/listings/add")
    public String addListing(@RequestParam String name,
                             @RequestParam String quantity,
                             @RequestParam String type) {
        User user = this.userService.findUserByMail().get();
        TypeOfFood t = TypeOfFood.valueOf(type);
        this.foodService.saveListingForGivenRestaurant(user.getRestaurant().getId(), name, quantity, t);

        return "redirect:/restaurants/listings";
    }

    @GetMapping("/addpage")
    public String addRestaurantPage(Model model) {
        return "restaurantAddPage";
    }

    @PostMapping("/addRestaurant")
    public String addRestaurant(@RequestParam String name,
                                @RequestParam String address,
                                @RequestParam String email,
                                @RequestParam String phone,
                                @RequestParam String city, @RequestParam String country) {

        Country c = Country.valueOf(country);
        this.restaurantService.save(name, phone, email, address, c, city);
        return "redirect:/auth/profile";
    }


    @PostMapping("/order/{id}")
    public String orderListing(@PathVariable Long id) {
        this.restaurantService.addListingToUser(id);
        return "redirect:/auth/profile";
    }

    @PostMapping("/order/remove/{id}")
    public String orderListingRemove(@PathVariable Long id) {
        Food food=this.foodService.findById(id)
                .orElseThrow(()->new FoodNotFoundException(id));
        this.restaurantService.removeListingFromUser(food);
        return "redirect:/auth/profile";
    }
}
