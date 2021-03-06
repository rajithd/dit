package com.dit.service.impl;

import com.dit.Notification;
import com.dit.account.User;
import com.dit.security.OAuth2Token;
import com.dit.service.*;
import com.dit.social.SocialNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;

//import org.springframework.social.google.api.Google;
//import org.springframework.social.google.connect.GoogleConnectionFactory;

@Service
public class SocialServiceImpl implements SocialService {

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private OAuth1Parameters oAuth1Parameters;

    @Qualifier("facebookOauth2Parameters")
    @Autowired
    private OAuth2Parameters facebookOAuth2Parameters;

    @Qualifier("googleOauth2Parameters")
    @Autowired
    private OAuth2Parameters googleOAuth2Parameters;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConnectionService userConnectionService;

    //TODO refactor this
    private OAuthToken requestToken;

    @Autowired
    private OAuth2TokenService oauth2TokenService;

    @Autowired
    private NotificationService notificationService;

    private static final String NOTIFICATION_KEY = "registerEmailNotification";
    private static final String ANALYTIC_KEY = "analyticKey";

    @Override
    public String buildTwitterAuthUrl() {
        TwitterConnectionFactory twitterConnectionFactory = (TwitterConnectionFactory) connectionFactoryLocator.getConnectionFactory(Twitter.class);
        OAuth1Operations oauthOperations = twitterConnectionFactory.getOAuthOperations();

        requestToken = oauthOperations.fetchRequestToken(
                oAuth1Parameters.getCallbackUrl(), null);

        return oauthOperations.buildAuthorizeUrl(requestToken.getValue(), OAuth1Parameters.NONE);
    }

    @Override
    public OAuth2Token createTwitterAuthUser(String oauthVerifier) {
        TwitterConnectionFactory twitterConnectionFactory = (TwitterConnectionFactory) connectionFactoryLocator.getConnectionFactory(Twitter.class);
        OAuth1Operations oauthOperations = twitterConnectionFactory.getOAuthOperations();

        OAuthToken accessToken = oauthOperations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier), null);
        Connection<Twitter> connection = twitterConnectionFactory.createConnection(accessToken);
        UserProfile userProfile = connection.fetchUserProfile();

        //validate the user is exists or not
        User filterUser = filterUser(SocialNetwork.TWITTER, userProfile.getUsername());
        if (filterUser == null) {
            //then create a user and oauth token
            User user = userService.buildUser(userProfile);
            user.setUserConnection(userConnectionService.buildUserConnection(userProfile, SocialNetwork.TWITTER));
            user = userService.save(user);
            filterUser = user;
        }
        return oauth2TokenService.save(oauth2TokenService.create(filterUser));
    }

    @Override
    public String buildFacebookAuthUrl() {
        FacebookConnectionFactory facebookConnectionFactory = (FacebookConnectionFactory) connectionFactoryLocator.getConnectionFactory(Facebook.class);
        OAuth2Operations oAuthOperations = facebookConnectionFactory.getOAuthOperations();
        return oAuthOperations.buildAuthorizeUrl(GrantType.IMPLICIT_GRANT, facebookOAuth2Parameters);
    }

    @Override
    public OAuth2Token createFacebookAuthUser(String accessToken) {
        FacebookConnectionFactory facebookConnectionFactory = (FacebookConnectionFactory) connectionFactoryLocator.getConnectionFactory(Facebook.class);
        AccessGrant accessGrant = new AccessGrant(accessToken);
        Connection<Facebook> connection = facebookConnectionFactory.createConnection(accessGrant);
        UserProfile userProfile = connection.fetchUserProfile();

        //validate the user is exists or not
        User filterUser = userService.findByEmail(userProfile.getEmail());
        if (filterUser == null) {
            //then create a user and oauth token
            User user = userService.buildUser(userProfile);
            user.setUserConnection(userConnectionService.buildUserConnection(userProfile, SocialNetwork.FACEBOOK));
            user = userService.save(user);
            filterUser = user;
        }
        filterUser.setUsername(userProfile.getEmail());

        Notification notification = NotificationFactory.buildNotification(filterUser, Collections.<String, String>emptyMap());
        notificationService.publishMessage(notification, NOTIFICATION_KEY);
        notificationService.publishMessage(filterUser, ANALYTIC_KEY);

        return oauth2TokenService.save(oauth2TokenService.create(filterUser));
    }

    @Override
    public String buildGoogleAuthUrl() {
//        GoogleConnectionFactory googleConnectionFactory = (GoogleConnectionFactory) connectionFactoryLocator.getConnectionFactory(Google.class);
//        OAuth2Operations oAuth2Operations = googleConnectionFactory.getOAuthOperations();
//        return oAuth2Operations.buildAuthorizeUrl(GrantType.IMPLICIT_GRANT, googleOAuth2Parameters);

        return null;
    }

    @Override
    public OAuth2Token createGoogleAuthUser(String accessToken) {
//        GoogleConnectionFactory googleConnectionFactory = (GoogleConnectionFactory) connectionFactoryLocator.getConnectionFactory(Google.class);
//        AccessGrant accessGrant = new AccessGrant(accessToken);
//        Connection<Google> connection = googleConnectionFactory.createConnection(accessGrant);
//        UserProfile userProfile = connection.fetchUserProfile();
//
//        //validate the user is exists or not
//        User filterUser = filterUser(SocialNetwork.GOOGLE, userProfile.getUsername());
//        if (filterUser == null) {
//            //then create a user and oauth token
//            User user = userService.buildUser(userProfile);
//            user.setUserConnection(userConnectionService.buildUserConnection(userProfile, SocialNetwork.GOOGLE));
//            user = userService.save(user);
//            filterUser = user;
//        }
//        return oauth2TokenService.save(oauth2TokenService.create(filterUser));

        return null;
    }

    private User filterUser(SocialNetwork socialNetwork, String username) {
        return userService.findByUserConnectionProviderId(username, socialNetwork.name());
    }
}
