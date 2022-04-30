package com.donations2go.donations2go.model.security;

import com.donations2go.donations2go.service.impl.CustomOAuth2UserService;
import com.donations2go.donations2go.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "/auth", "/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth").permitAll()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(oauthUserService).and()
                .successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {

                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                        String email=null;
                        if(oauthUser.getLogin()!=null){
                            email=oauthUser.getLogin();
                        }
                        else {
                            email = oauthUser.getEmail();
                        }
                        int num=userService.processOAuthPostLogin(email);
                        if(num==1){
                            response.sendRedirect("/postregister");
                        }
                        else {
                            response.sendRedirect("/restaurants/listings");
                        }
                    }
                });

    }


}
