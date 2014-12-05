package com.dit.service.impl;

import com.dit.repository.OAuth2TokenRepository;
import com.dit.security.OAuth2Token;
import com.dit.service.OAuth2TokenService;
import com.dit.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class OAuth2TokenServiceImpl implements OAuth2TokenService {

    @Autowired
    private OAuth2TokenRepository oAuth2TokenRepository;

    @Override
    public OAuth2Token findByUsernameAndValue(String username, String value) {
        return oAuth2TokenRepository.findByUsernameAndValue(username, value);
    }

    @Override
    public OAuth2Token findByValue(String value) {
        return oAuth2TokenRepository.findByValue(value);
    }

    @Override
    public OAuth2Token create(String username) {
        OAuth2Token oauth2Token = new OAuth2Token();
        oauth2Token.setUsername(username);
        oauth2Token.setValue(SecurityUtil.generateHash(username));
        oauth2Token.setExpiryAt(addOneDay());
        return oauth2Token;
    }

    @Override
    public OAuth2Token save(OAuth2Token oAuth2Token) {
        return oAuth2TokenRepository.save(oAuth2Token);
    }

    private Date addOneDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        return calendar.getTime();
    }
}
