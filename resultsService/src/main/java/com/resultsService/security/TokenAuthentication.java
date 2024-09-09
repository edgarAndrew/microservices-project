package com.resultsService.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.Collections;

public class TokenAuthentication implements Authentication {

    private final String token;
    private boolean authenticated;

    public TokenAuthentication(String token, boolean authenticated) {
        this.token = token;
        this.authenticated = authenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return token;
    }
}

