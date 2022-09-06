package com.restaurant.restaurant;

import com.restaurant.restaurant.utils.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestaurantApplication {
    @Bean
    public ModelMapper modelMapper(){ return new ModelMapper();}
    @Bean
    public Helper helper(){ return new Helper();}

    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);
    }

}
