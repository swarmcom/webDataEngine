package security.service;

import domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import security.domain.SecurityUserDetails;
import service.UserService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityDaoUserDetailsService implements UserDetailsService {

    @Inject
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.getUser(userName);
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
