package com.dit.repository;

import com.dit.account.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    @Query("{'username':?0, 'password':?1}")
    List<User> findByUsernameAndPassword(String username, String password);

    @Query("{'username':?0, 'userConnection.providerId':?1}")
    User findByUsernameAndProviderId(String username, String providerId);

    User findByEmail(String email);

}
