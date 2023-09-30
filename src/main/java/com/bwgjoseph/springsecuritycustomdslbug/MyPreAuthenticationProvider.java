package com.bwgjoseph.springsecuritycustomdslbug;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class MyPreAuthenticationProvider implements AuthenticationProvider {

    private boolean throwExceptionWhenTokenRejected;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
          return null;
        }

        if (authentication.getPrincipal() == null) {
          if (this.throwExceptionWhenTokenRejected) {
            throw new BadCredentialsException("No pre-authenticated principal found in request.");
          }
          return null;
        }

        UserDetails rud = new User("bwgjoseph", "pw", true, true, true, true, List.of(new SimpleGrantedAuthority("RW")));
        PreAuthenticatedAuthenticationToken result = new PreAuthenticatedAuthenticationToken(rud,
            authentication.getCredentials(), rud.getAuthorities());
        result.setDetails(authentication.getDetails());

        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setThrowExceptionWhenTokenRejected(boolean throwExceptionWhenTokenRejected) {
        this.throwExceptionWhenTokenRejected = throwExceptionWhenTokenRejected;
    }

}
