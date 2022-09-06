package com.restaurant.restaurant.service;

import com.restaurant.restaurant.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService{
    public User save(User user);
    public User findByEmail(String email);
    public Boolean existByEmail(String email);
}
