package com.restaurant.restaurant.service.impl;

import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.repository.UserRepository;
import com.restaurant.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean existByEmail(String email) {
       return userRepository.existsByEmail(email);
    }
}
