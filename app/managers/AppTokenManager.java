package managers;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.http.credentials.UsernamePasswordCredentials;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.credentials.OidcCredentials;
import org.pac4j.play.store.DataStore;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import play.Logger;
import play.mvc.Http;
import security.token.ClientToken;
import security.token.ClientType;
import security.token.SecurityUsernamePasswordAuthenticationToken;
import security.token.SessionAuthenticationToken;

import javax.inject.Inject;

@Component
public class AppTokenManager {
    @Inject
    protected DataStore dataStore;

    @Inject
    private DirectBasicAuthClient basicClient;

    @Inject
    protected FormClient formClient;

    @Inject
    protected OidcClient oidcClient;

    @Inject
    private AppProfileManager profileManager;

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
        if (initialToken != null) {
            SecurityContextHolder.getContext().setAuthentication(initialToken);
        }
        return initialToken;
    }

    public Authentication retrieveExistingToken() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Authentication createBasicInitialToken(Http.Context context) throws Exception {
        WebContext profileContext = profileManager.getProfileContext(context);
        UsernamePasswordCredentials credentials = (UsernamePasswordCredentials)basicClient.getCredentials(profileContext);
        Logger.info("Check form basic " + credentials);
        if (credentials != null) {
            return new SecurityUsernamePasswordAuthenticationToken(credentials, ClientType.DirectBasicAuthClient);
        }
        return null;
    }

    public Authentication createFormInitialToken(Http.Context context) throws Exception {
        WebContext profileContext = profileManager.getProfileContext(context);
        UsernamePasswordCredentials credentials = (UsernamePasswordCredentials)formClient.getCredentials(profileContext);
        Logger.info("Check form secured " + credentials);
        if (credentials != null) {
            return new SecurityUsernamePasswordAuthenticationToken(credentials, ClientType.FormClient);
        }
        return null;
    }

    public Authentication createOidcInitialToken(Http.Context context) throws Exception {
        WebContext profileContext = profileManager.getProfileContext(context);
        OidcCredentials credentials = (OidcCredentials)oidcClient.getCredentials(profileContext);
        Logger.info("Check oidc secured " + credentials);
        if (credentials != null) {
            return new ClientAuthenticationToken(credentials, "OidcClient");
        }
        return null;
    }

    public Authentication createSessionInitialToken(Http.Context context) throws Exception {
        UserProfile profile = profileManager.getUserProfile(context);
        Logger.info("Check session secured " + profile);
        if (profile != null) {
            return new SessionAuthenticationToken(profile, ClientType.SessionClient);
        }
        return null;
    }

    public void clearRequestAuthenticationToken() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
