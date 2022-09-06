package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Boolean existsByEmail(String email);
    public User findByEmail(String email);
}
