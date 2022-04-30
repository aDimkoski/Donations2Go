package com.donations2go.donations2go.service;

import com.donations2go.donations2go.model.Food;
import com.donations2go.donations2go.model.Restaurant;
import com.donations2go.donations2go.model.enums.Country;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    List<Restaurant> findAll();
    Optional<Restaurant> findById(Long id);

    Optional<Restaurant> save(String name, String phone, String email, String address, Country country, String city);

    Optional<Restaurant> addListingToUser(Long foodId);
    Optional<Restaurant> findByFood(Long id);

    Optional<Food> removeListingFromUser(Food food);
}
