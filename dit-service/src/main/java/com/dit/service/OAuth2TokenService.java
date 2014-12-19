package com.dit.service;

import com.dit.account.User;
import com.dit.security.OAuth2Token;

public interface OAuth2TokenService {

    OAuth2Token findByUsernameAndValue(String username, String value);

    OAuth2Token findByValue(String value);

    OAuth2Token create(User user);

    OAuth2Token save(OAuth2Token oAuth2Token);

}
