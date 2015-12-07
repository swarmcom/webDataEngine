package security.providers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import play.Logger;
import security.encoder.SecurityPasswordEncoder;
import security.service.SecurityDaoUserDetailsService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
public class SecurityDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Inject
    private SecurityDaoUserDetailsService userDetailsService;

    @Inject
    private SecurityPasswordEncoder passwordEncoder;


    @PostConstruct
    public void init() {
        //setPasswordEncoder(passwordEncoder);
        setUserDetailsService(userDetailsService);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication auth = super.authenticate(authentication);
        Logger.info("MIRCEA" + auth.getAuthorities());
        return auth;
    }
}
