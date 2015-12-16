package security.service;

import org.pac4j.core.profile.UserProfile;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import play.Logger;
import security.domain.SecurityUserDetails;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecuritySocialUserDetailsService implements AuthenticationUserDetailsService<ClientAuthenticationToken> {

    @Override
    public UserDetails loadUserDetails(ClientAuthenticationToken clientAuthenticationToken) throws UsernameNotFoundException {
        UserProfile profile = clientAuthenticationToken.getUserProfile();
        if (profile != null) {
            List<GrantedAuthority> rolesList = new ArrayList<GrantedAuthority>();
            rolesList.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new SecurityUserDetails(profile.getTypedId(), null, rolesList);
        }
        return null;
    }
}