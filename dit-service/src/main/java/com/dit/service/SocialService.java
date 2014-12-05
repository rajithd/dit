package com.dit.service;

import com.dit.security.OAuth2Token;

public interface SocialService {

    /**
     * Build twitter auth url based on
     * @return twitter auth url
     */
    String buildTwitterAuthUrl();

    /**
     * Create twitter profile
     * @param oauthVerifier twitter oauthToken
     */
    OAuth2Token createTwitterAuthUser(String oauthVerifier);

    /**
     * Build facebook url
     * @return
     */
    String buildFacebookAuthUrl();

    /**
     * Create facebook profile
     * @param accessToken
     */
    OAuth2Token createFacebookAuthUser(String accessToken);

    /**
     * Build google auth url
     * @return
     */
    String buildGoogleAuthUrl();

    /**
     * create google profile
     * @param accessToken
     */
    OAuth2Token createGoogleAuthUser(String accessToken);

}
