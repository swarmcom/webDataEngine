package security.service;

import api.domain.User;
import api.service.MultiUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import security.domain.SecurityUserDetails;
import security.util.TokenUtil;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityDaoUserDetailsService implements UserDetailsService {

    @Inject
    MultiUserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.getUser(TokenUtil.getCurrentAccountId(), userName);
        if (user != null) {
            List<GrantedAuthority> rolesList = new ArrayList<GrantedAuthority>();
            for (String role : user.getRoles()) {
                rolesList.add(new SimpleGrantedAuthority(role));
            }
            return new SecurityUserDetails(user.getUserName(), user.getPassword(), rolesList);
        }
        return null;
    }
}
