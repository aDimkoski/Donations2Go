package com.donations2go.donations2go.model;

import com.donations2go.donations2go.model.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private String email;

    private String name;

    private String surname;

    private String phone;

    private String privateAddress;

    private String city;

    private String country;

    @Column(length = 2000)
    private String profilePictureLink;

    private String jobTitle;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToOne
    private Restaurant restaurant;

    @OneToOne
    private Foodbank foodbank;


    @ManyToMany
    private List<Food> foodList;
}
