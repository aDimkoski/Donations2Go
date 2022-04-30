package com.donations2go.donations2go.repository;

import com.donations2go.donations2go.model.Foodbank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodBankRepository extends JpaRepository<Foodbank,Long> {
}
