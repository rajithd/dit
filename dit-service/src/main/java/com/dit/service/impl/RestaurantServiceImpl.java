package com.dit.service.impl;

import com.dit.Restaurant;
import com.dit.repository.RestaurantRepository;
import com.dit.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant findById(String id) {
        return restaurantRepository.findOne(id);
    }

    @Override
    public Restaurant findByRegNo(String regNo) {
        return restaurantRepository.findByRegNo(regNo);
    }
}
