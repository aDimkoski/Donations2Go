package com.donations2go.donations2go.service.impl;

import com.donations2go.donations2go.model.User;
import com.donations2go.donations2go.model.enums.Role;
import com.donations2go.donations2go.model.exceptions.UserNotFoundException;
import com.donations2go.donations2go.model.security.CustomOAuth2User;
import com.donations2go.donations2go.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;


    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public boolean isAuthenticated(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.getPrincipal().equals("anonymousUser")){
            return true;
        }
        return false;
    }
    public List<User> findAllByRole(){
        return this.repo.findAllByRole(Role.ROLE_RESTAURANT);
    }
    public Optional<User> findUserByMail(){
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
        return Optional.of(this.repo.findById(mail)).orElseThrow(()->new UserNotFoundException(finalMail));
    }

    public void updateUser(String name, String surname,String phone,String address,
                           String country, String city, String picture,String job, Role role){
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
        User user=this.repo.findById(mail).orElseThrow(()->new UserNotFoundException(finalMail));
        user.setEmail(mail);
        user.setName(name);
        user.setPhone(phone);
        user.setPrivateAddress(address);
        user.setCountry(country);
        user.setCity(city);
        user.setProfilePictureLink(picture);
        user.setJobTitle(job);
        user.setSurname(surname);
        user.setRole(role);
        this.repo.save(user);
    }

    public int processOAuthPostLogin(String username) {
        Optional<User> existUser = repo.findById(username);

        if (existUser.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(username);
            newUser.setSurname("hello");
            newUser.setName("hello2");

            repo.save(newUser);
            return 1;
        }
        return 0;
    }

}
