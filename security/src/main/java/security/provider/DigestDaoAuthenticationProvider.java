package security.provider;

import org.pac4j.core.profile.UserProfile;
import org.pac4j.http.credentials.DigestCredentials;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import security.encoder.DigestPasswordEncoder;
import security.service.SecurityDaoUserDetailsService;
import security.token.DigestAuthenticationToken;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;

@Component
public class DigestDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Inject
    private SecurityDaoUserDetailsService userDetailsService;

    @Inject
    private DigestPasswordEncoder passwordEncoder;


    @PostConstruct
    public void init() {
        setPasswordEncoder(passwordEncoder);
        setUserDetailsService(userDetailsService);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(DigestAuthenticationToken.class, authentication, this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports", "Only DigestAuthenticationToken is supported"));
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)super.authenticate(authentication);
        DigestAuthenticationToken digestToken = (DigestAuthenticationToken)authentication;
        DigestAuthenticationToken authenticatedToken = new DigestAuthenticationToken(auth);
        authenticatedToken.setDigestCredentials(digestToken.getDigestCredentials());
        authenticatedToken.setClientType(digestToken.getClientType());
        UserProfile userProfile = authenticatedToken.getDigestCredentials().getUserProfile();

        Collection<? extends GrantedAuthority> grantedAuthorities = authenticatedToken.getAuthorities();
        for (GrantedAuthority authority : grantedAuthorities) {
            userProfile.addRole(authority.getAuthority());
        }

        return authenticatedToken;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        DigestAuthenticationToken authentication = (DigestAuthenticationToken)usernamePasswordAuthenticationToken;
        if(authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            DigestCredentials credentials = authentication.getDigestCredentials();
            String clientResponse = credentials.getToken();
            String userPassword = userDetails.getPassword();
            String serverResponse = passwordEncoder.getServerResponse(userPassword, credentials);
            if(!this.passwordEncoder.matches(clientResponse, serverResponse)) {
                this.logger.debug("Authentication failed: digest token from server does not match");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        UserDetails loadedUser;
        try {
            loadedUser = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException unf) {
            throw unf;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }

        if(loadedUser == null) {
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        } else {
            return loadedUser;
        }
    }

    public void setPasswordEncoder(DigestPasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
    }

    public void setUserDetailsService(SecurityDaoUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
