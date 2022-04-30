package com.donations2go.donations2go.service.impl;

import com.donations2go.donations2go.model.Foodbank;
import com.donations2go.donations2go.model.User;
import com.donations2go.donations2go.model.exceptions.UserNotFoundException;
import com.donations2go.donations2go.model.security.CustomOAuth2User;
import com.donations2go.donations2go.repository.FoodBankRepository;
import com.donations2go.donations2go.repository.UserRepository;
import com.donations2go.donations2go.service.FoodBankService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodBankServiceImpl implements FoodBankService {
    private final FoodBankRepository foodBankRepository;

    private final UserRepository userRepository;
    public FoodBankServiceImpl(FoodBankRepository foodBankRepository, UserRepository userRepository) {
        this.foodBankRepository = foodBankRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Foodbank> findAll() {
        return this.foodBankRepository.findAll();
    }

    @Override
    public Optional<Foodbank> findById(Long id) {
        return this.foodBankRepository.findById(id);
    }

    @Override
    public Optional<Foodbank> save(String name, String num) {
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

        Foodbank foodbank=new Foodbank(name,num);

        this.foodBankRepository.save(foodbank);

        user.setFoodbank(foodbank);

        this.userRepository.save(user);

        return Optional.of(foodbank);
    }

}
