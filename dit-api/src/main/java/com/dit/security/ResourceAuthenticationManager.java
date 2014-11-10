package com.dit.security;

import com.dit.account.User;
import com.dit.service.OAuth2TokenService;
import com.dit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class ResourceAuthenticationManager implements AuthenticationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceAuthenticationManager.class);

    @Autowired
    private OAuth2TokenService oauth2TokenService;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = authentication.getPrincipal().toString();
        if (!principal.startsWith("Bearer")) {
            throw new AuthenticationCredentialsNotFoundException("No Bearer Token!");
        }
        String bearerToken = principal.substring("Bearer".length()).trim();
        OAuth2Token oAuth2Token = oauth2TokenService.findByValue(bearerToken);
        validateObj(oAuth2Token);
        User user = userService.findByUsername(oAuth2Token.getUsername());
        UsernamePasswordAuthenticationToken returnAuth = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), null);
        LOGGER.debug(authentication.getPrincipal().toString());
        return returnAuth;
    }

    private void validateObj(Object object) {
        if (object == null) {
            throw new BadCredentialsException("Unauthorized access");
        }
    }
}
