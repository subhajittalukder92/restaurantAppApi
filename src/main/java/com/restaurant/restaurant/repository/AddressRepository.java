package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
