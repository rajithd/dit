package com.dit.connector;

import com.dit.response.Success;
import com.dit.security.OAuth2Token;
import com.dit.service.SocialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/public/client/auth")
public class SocialSignInConnector {

    @Autowired
    private SocialService socialService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/twitter/url", produces = "application/json")
    @ResponseBody
    public ResponseEntity getTwitterOauthUrl() {
        return new ResponseEntity<Success>(new Success(socialService.buildTwitterAuthUrl()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/twitter/callback")
    @ResponseBody
    public OAuth2Token getTwitterCallbackUrl(@RequestParam("oauth_token") String oauthToken, @RequestParam("oauth_verifier") String oauthVerifier, HttpServletRequest request) {
        return socialService.createTwitterAuthUser(oauthVerifier);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/facebook/url", produces = "application/json")
    @ResponseBody
    public ResponseEntity getFacebookOauthUrl() {
        return new ResponseEntity<Success>(new Success(socialService.buildFacebookAuthUrl()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/facebook/callback")
    @ResponseBody
    public OAuth2Token getFacebookCallbackUrl(@RequestParam("access_token") String accessToken, HttpServletRequest request) {
        return socialService.createFacebookAuthUser(accessToken);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/google/url", produces = "application/json")
    @ResponseBody
    public ResponseEntity getGoogleOauthUrl() {
        return new ResponseEntity<Success>(new Success(socialService.buildGoogleAuthUrl()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/google/callback")
    @ResponseBody
    public OAuth2Token getGoogleCallbackUrl(@RequestParam("access_token") String accessToken, HttpServletRequest request) {
        return socialService.createGoogleAuthUser(accessToken);
    }
}