package security.auth;

import org.pac4j.http.credentials.UsernamePasswordCredentials;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SecurityUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    UsernamePasswordCredentials userPwdCredentials;

    public SecurityUsernamePasswordAuthenticationToken(UsernamePasswordCredentials credentials) {
        super(credentials.getUsername(), credentials.getPassword());
        this.userPwdCredentials = credentials;
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
}
