package security.providers;


import org.pac4j.core.profile.UserProfile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import play.Logger;
import security.token.SecurityUsernamePasswordAuthenticationToken;
import security.service.SecurityDaoUserDetailsService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;

@Component
public class SecurityDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Inject
    private SecurityDaoUserDetailsService userDetailsService;

    @Inject
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    public void init() {
        setPasswordEncoder(passwordEncoder);
        setUserDetailsService(userDetailsService);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Logger.info("Authentication MIRCEA");
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)super.authenticate(authentication);
        SecurityUsernamePasswordAuthenticationToken userPwdToken = (SecurityUsernamePasswordAuthenticationToken)authentication;
        SecurityUsernamePasswordAuthenticationToken authenticatedToken = new SecurityUsernamePasswordAuthenticationToken(auth);
        authenticatedToken.setUserPwdCredentials(userPwdToken.getUserPwdCredentials());
        authenticatedToken.setClientType(userPwdToken.getClientType());

        UserProfile userProfile = authenticatedToken.getUserPwdCredentials().getUserProfile();

        Collection<? extends GrantedAuthority> grantedAuthorities = authenticatedToken.getAuthorities();
        for (GrantedAuthority authority : grantedAuthorities) {
            userProfile.addRole(authority.getAuthority());
        }

        return authenticatedToken;
    }
}
