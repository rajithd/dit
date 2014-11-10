package com.dit.social;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class SpringSocialConfig {

    @Value("${twitter.consumer.key}")
    private String twitterConsumerKey;

    @Value("${twitter.consumer.secret}")
    private String twitterConsumerSecret;

    @Value("${twitter.callback.url}")
    private String twitterCallBackUrl;

    @Value("${facebook.consumer.key}")
    private String facebookConsumerKey;

    @Value("${facebook.consumer.secret}")
    private String facebookConsumerSecret;

    @Value("${facebook.callback.url}")
    private String facebookCallBackUrl;

    @Value("${google.consumer.key}")
    private String googleConsumerKey;

    @Value("${google.consumer.secret}")
    private String googleConsumerSecret;

    @Value("${google.callback.url}")
    private String googleCallBackUrl;

    @Bean
    @Scope(value = "singleton")
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();

//        // Register Facebook Connection Factory
        registry.addConnectionFactory(new FacebookConnectionFactory(
                facebookConsumerKey, facebookConsumerSecret));

        // Register Twitter Connection Factory
        registry.addConnectionFactory(new TwitterConnectionFactory(
                twitterConsumerKey, twitterConsumerSecret));

        //Register Google connection factory
        registry.addConnectionFactory(new GoogleConnectionFactory(googleConsumerKey, googleConsumerSecret));
        return registry;
    }

    @Bean
    public OAuth1Parameters twitterOAuth1Parameters() {
        OAuth1Parameters params = new OAuth1Parameters();
        params.setCallbackUrl(twitterCallBackUrl);
        return params;
    }

    @Bean(name = "facebookOauth2Parameters")
    public OAuth2Parameters facebookOauth2Parameters() {
        OAuth2Parameters params = new OAuth2Parameters();
        params.setScope("email");
        params.setRedirectUri(facebookCallBackUrl);
        return params;
    }

    @Bean(name = "googleOauth2Parameters")
    public OAuth2Parameters gogoleOauth2Parameters() {
        OAuth2Parameters params = new OAuth2Parameters();
        params.setScope("email");
        params.setRedirectUri(googleCallBackUrl);
        return params;
    }
}
