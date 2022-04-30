package com.donations2go.donations2go.service.impl;

import com.donations2go.donations2go.model.Food;
import com.donations2go.donations2go.model.Restaurant;
import com.donations2go.donations2go.model.enums.TypeOfFood;
import com.donations2go.donations2go.model.exceptions.RestaurantNotFoundException;
import com.donations2go.donations2go.repository.FoodRepository;
import com.donations2go.donations2go.repository.RestaurantRepository;
import com.donations2go.donations2go.service.FoodService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    private final RestaurantRepository restaurantRepository;

    public FoodServiceImpl(FoodRepository foodRepository, RestaurantRepository restaurantRepository) {
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Food> findAll() {
        return this.foodRepository.findAll();
    }

    @Override
    public Optional<Food> findById(Long id) {
        return this.foodRepository.findById(id);
    }

    @Override
    public Optional<Food> saveListingForGivenRestaurant(Long id, String name, String quantity, TypeOfFood typeOfFood) {
        Restaurant restaurant=this.restaurantRepository.findById(id)
                .orElseThrow(()-> new RestaurantNotFoundException(id));

        Food food=new Food(name,quantity,typeOfFood,restaurant);
        this.foodRepository.save(food);
        restaurant.getFoodList().add(food);
        this.restaurantRepository.save(restaurant);

        return Optional.of(food);
    }
}
