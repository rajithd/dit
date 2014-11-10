package com.dit.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BearerTokenPreAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BearerTokenPreAuthFilter.class);

    @Autowired
    private ResourceAuthenticationManager resourceAuthenticationManager;

    @Override
    public void afterPropertiesSet() {
        this.setAuthenticationManager(resourceAuthenticationManager);
        super.afterPropertiesSet();
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader("Authorization");
        if(bearer == null){
            throw new PreAuthenticatedCredentialsNotFoundException("Authorization header not found in request.");
        }
        return bearer;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest httpServletRequest) {
        return null;
    }
}
