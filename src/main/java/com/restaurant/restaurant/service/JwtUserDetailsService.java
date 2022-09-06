package com.restaurant.restaurant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.jwtsecurity.CustomUserDetails;
import com.restaurant.restaurant.payload.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("No Such Email Found!");
        }
        return new CustomUserDetails(user);

    }

}
