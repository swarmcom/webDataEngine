package managers;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.oidc.credentials.OidcCredentials;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.store.DataStore;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import play.mvc.Http;
import security.token.ClientToken;
import security.token.ClientType;

import javax.inject.Inject;

@Component
public class AppTokenManager {
    @Inject
    protected DataStore dataStore;

    public String getUsernameFromToken(Authentication token) {
        if (token instanceof ClientAuthenticationToken) {
            ClientAuthenticationToken clientToken = (ClientAuthenticationToken)token;
            if(StringUtils.equals(clientToken.getClientName(), ClientType.OidcClient.name())) {
                OidcCredentials oidcCredentials = (OidcCredentials)clientToken.getCredentials();
                return oidcCredentials.getCode().getValue();
            }
        } else if (token instanceof ClientToken) {
            ClientToken clientToken = (ClientToken) token;
            if (clientToken.getClientType() == ClientType.FormClient ||
                    clientToken.getClientType() == ClientType.SessionClient || clientToken.getClientType() == ClientType.DirectBasicAuthClient) {
                Object principal = token.getPrincipal();
                if (principal != null) {
                    return principal.toString();
                }
            }
        }
        return null;
    }

    public Authentication applyInitialToken(Http.Context ctx, Authentication initialToken) throws Exception {
        PlayWebContext context = new PlayWebContext(ctx, dataStore);
        SecurityContextHolder.getContext().setAuthentication(initialToken);
        return initialToken;
    }

    public Authentication retrieveExistingToken() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
