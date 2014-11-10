package com.dit.repository;

import com.dit.security.OAuth2Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2TokenRepository extends MongoRepository<OAuth2Token,String > {

    OAuth2Token findByUsernameAndValue(String username, String value);

    OAuth2Token findByValue(String value);

}
