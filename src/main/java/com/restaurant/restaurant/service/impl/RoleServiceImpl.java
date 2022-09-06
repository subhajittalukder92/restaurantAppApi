package com.restaurant.restaurant.service.impl;

import com.restaurant.restaurant.entity.Role;
import com.restaurant.restaurant.exception.ResourceNotFoundException;
import com.restaurant.restaurant.repository.RoleRepository;
import com.restaurant.restaurant.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository ;
    @Override
    public Role save(Role role) {
       Role result = roleRepository.save(role);
       return result;
    }

    @Override
    public Role findById(Long id) {
       Role role = roleRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Role", "role id", id));
        return role;
    }
}
