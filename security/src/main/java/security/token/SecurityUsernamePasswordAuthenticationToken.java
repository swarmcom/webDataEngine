package security.token;

import org.pac4j.http.credentials.UsernamePasswordCredentials;
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

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}
