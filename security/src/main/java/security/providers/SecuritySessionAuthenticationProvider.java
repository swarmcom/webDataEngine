package security.providers;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import play.Logger;
import security.token.SessionAuthenticationToken;

@Component
public class SecuritySessionAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(SessionAuthenticationToken.class, authentication, "Only SessionAuthenticationToken is supported");
        SessionAuthenticationToken sessionToken = (SessionAuthenticationToken)authentication;
        if (sessionToken.isAuthenticated()) {
            return sessionToken;
        } else {
            throw new CredentialsExpiredException("Session invalid");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SessionAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
