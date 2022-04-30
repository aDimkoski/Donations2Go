package com.donations2go.donations2go.model;

import com.donations2go.donations2go.model.enums.TypeOfFood;
import com.donations2go.donations2go.repository.RestaurantRepository;
import com.donations2go.donations2go.service.RestaurantService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Data
public class Food {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String quantity;

    @ManyToOne
    private Restaurant restaurant;
    @Enumerated(EnumType.STRING)
    private TypeOfFood typeOfFood;

    public Food(String name, String quantity, TypeOfFood typeOfFood,Restaurant restaurant) {
        this.name = name;
        this.quantity = quantity;
        this.typeOfFood = typeOfFood;
        this.restaurant=restaurant;
    }

    public Food() {

    }

}
