package com.donations2go.donations2go.service;

import com.donations2go.donations2go.model.Food;
import com.donations2go.donations2go.model.enums.TypeOfFood;

import java.util.List;
import java.util.Optional;

public interface FoodService {
    List<Food> findAll();
    Optional<Food> findById(Long id);

    Optional<Food> saveListingForGivenRestaurant(Long id, String name, String quantity, TypeOfFood typeOfFood);
}
