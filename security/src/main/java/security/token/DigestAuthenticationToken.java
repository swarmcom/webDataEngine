package security.token;

import org.pac4j.core.profile.UserProfile;
import org.pac4j.http.credentials.DigestCredentials;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class DigestAuthenticationToken extends UsernamePasswordAuthenticationToken implements ClientToken {

    DigestCredentials digestCredentials;
    ClientType clientType;

    public DigestAuthenticationToken(DigestCredentials credentials, ClientType clientType) {
        super(credentials.getUsername(), credentials.getToken());
        this.digestCredentials = credentials;
        this.clientType = clientType;
    }

    public DigestAuthenticationToken(UsernamePasswordAuthenticationToken token) {
        super(token.getPrincipal(), token.getCredentials(), token.getAuthorities());
    }

    public DigestCredentials getDigestCredentials() {
        return digestCredentials;
    }

    public void setDigestCredentials(DigestCredentials digestCredentials) {
        this.digestCredentials = digestCredentials;
    }

    public ClientType getClientType() {
        return clientType;
    }

    @Override
    public UserProfile getUserProfile() {
        return digestCredentials.getUserProfile();
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}