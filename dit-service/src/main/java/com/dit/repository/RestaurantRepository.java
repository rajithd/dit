package com.dit.repository;

import com.dit.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    Restaurant findByRegNo(String regNo);


}
