package com.restaurant.restaurant.service;

import com.restaurant.restaurant.entity.Role;

public interface RoleService {
    public Role save(Role role);
    public Role findById(Long id);
}
