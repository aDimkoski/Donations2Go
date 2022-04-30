package com.donations2go.donations2go.service.impl;

import com.donations2go.donations2go.model.Food;
import com.donations2go.donations2go.model.Restaurant;
import com.donations2go.donations2go.model.User;
import com.donations2go.donations2go.model.enums.Country;
import com.donations2go.donations2go.model.exceptions.FoodNotFoundException;
import com.donations2go.donations2go.model.exceptions.UserNotFoundException;
import com.donations2go.donations2go.model.security.CustomOAuth2User;
import com.donations2go.donations2go.repository.FoodRepository;
import com.donations2go.donations2go.repository.RestaurantRepository;
import com.donations2go.donations2go.repository.UserRepository;
import com.donations2go.donations2go.service.FoodService;
import com.donations2go.donations2go.service.RestaurantService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    private final FoodService foodService;

    private final UserRepository userRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, FoodRepository foodRepository, FoodService foodService, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.foodService = foodService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Restaurant> findAll() {
        return this.restaurantRepository.findAll();
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return this.restaurantRepository.findById(id);
    }

    @Override
    public Optional<Restaurant> findByFood(Long id) {
        Food food=this.foodRepository.findById(id)
                .orElseThrow(()->new FoodNotFoundException(id));
        return Optional.of(this.restaurantRepository.findFirstByFoodList(food));
    }

    @Override
    @Transactional
    public Optional<Restaurant> save(String name, String phone, String email, String address, Country country,String city) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        String mail=null;
        if(oauthUser.getLogin()!=null) {
            mail = oauthUser.getLogin();
        }
        else {
            mail = oauthUser.getEmail();
        }

        String finalMail = mail;
        User user=this.userRepository.findById(mail).orElseThrow(()->new UserNotFoundException(finalMail));

        Restaurant restaurant=new Restaurant(name,phone,email,address,country,city);
        this.restaurantRepository.save(restaurant);

        user.setRestaurant(restaurant);
        this.userRepository.save(user);
        return Optional.of(this.restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional
    public Optional<Restaurant> addListingToUser(Long foodId){
        Food food=this.foodRepository.findById(foodId)
                .orElseThrow(()->new FoodNotFoundException(foodId));
        Restaurant restaurant=this.restaurantRepository.findFirstByFoodList(food);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        String mail=null;
        if(oauthUser.getLogin()!=null) {
            mail = oauthUser.getLogin();
        }
        else {
            mail = oauthUser.getEmail();
        }

        String finalMail = mail;
        User user=this.userRepository.findById(mail).orElseThrow(()->new UserNotFoundException(finalMail));

        food.getRestaurant().getFoodList().remove(food);
        this.restaurantRepository.save(food.getRestaurant());
        user.getFoodList().add(food);

        this.userRepository.save(user);

        return Optional.of(restaurant);
    }
    @Override
    @Transactional
    public Optional<Food> removeListingFromUser(Food food){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        String mail=null;
        if(oauthUser.getLogin()!=null) {
            mail = oauthUser.getLogin();
        }
        else {
            mail = oauthUser.getEmail();
        }

        String finalMail = mail;
        User user=this.userRepository.findById(mail).orElseThrow(()->new UserNotFoundException(finalMail));

        this.foodService.saveListingForGivenRestaurant(food.getRestaurant().getId(),food.getName(),food.getQuantity(),food.getTypeOfFood());
        user.getFoodList().remove(food);

        this.userRepository.save(user);

        return Optional.of(food);
    }
}
