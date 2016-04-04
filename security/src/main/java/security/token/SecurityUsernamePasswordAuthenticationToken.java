package security.token;

import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.profile.UserProfile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SecurityUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken implements ClientToken {

    UsernamePasswordCredentials userPwdCredentials;
    ClientType clientType;

    public SecurityUsernamePasswordAuthenticationToken(UsernamePasswordCredentials credentials, ClientType clientType) {
        super(credentials.getUsername(), credentials.getPassword());
        this.userPwdCredentials = credentials;
        this.clientType = clientType;
    }

    public SecurityUsernamePasswordAuthenticationToken(UsernamePasswordAuthenticationToken token) {
        super(token.getPrincipal(), token.getCredentials(), token.getAuthorities());
    }

    public UsernamePasswordCredentials getUserPwdCredentials() {
        return userPwdCredentials;
    }

    public void setUserPwdCredentials(UsernamePasswordCredentials userPwdCredentials) {
        this.userPwdCredentials = userPwdCredentials;
    }

    public ClientType getClientType() {
        return clientType;
    }

    @Override
    public UserProfile getUserProfile() {
        return userPwdCredentials.getUserProfile();
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}
