package security.token;

import org.pac4j.core.profile.UserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class SessionAuthenticationToken implements Authentication, ClientToken {

    UserProfile profile;
    ClientType clientType;

    public SessionAuthenticationToken(UserProfile profile, ClientType clientType) {
        this.profile = profile;
        this.clientType = clientType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (profile != null) {
            for (String role : profile.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return profile != null ? profile.getTypedId() : null;
    }

    @Override
    public boolean isAuthenticated() {
        return profile == null ? false : true;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }

    public ClientType getClientType() {
        return clientType;
    }
}
