package com.dit.service;

import com.dit.Restaurant;

public interface RestaurantService {

    public Restaurant save(Restaurant restaurant);

    public Restaurant findById(String id);

}
