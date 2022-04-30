package com.donations2go.donations2go.repository;

import com.donations2go.donations2go.model.Food;
import com.donations2go.donations2go.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Restaurant findFirstByFoodList(Food food);
}
