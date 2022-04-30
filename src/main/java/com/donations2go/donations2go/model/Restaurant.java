package com.donations2go.donations2go.model;

import com.donations2go.donations2go.model.enums.Country;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String phoneNumber;

    private String email;
    private String address;

    private Country country;

    private String city;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Food> foodList;

    public Restaurant(String name, String phoneNumber, String email, String address, Country country, String city) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.country = country;
        this.city = city;
    }

    public Restaurant() {

    }
}
