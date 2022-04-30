package com.donations2go.donations2go.service;

import com.donations2go.donations2go.model.Foodbank;

import java.util.List;
import java.util.Optional;

public interface FoodBankService {
    List<Foodbank> findAll();
    Optional<Foodbank> findById(Long id);
    Optional<Foodbank> save(String name, String num);
}
