package auth;

import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.credentials.OidcCredentials;
import org.pac4j.play.PlayWebContext;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.core.Authentication;
import play.Logger;

import javax.inject.Inject;


public class OidcSecured extends Secured {
    @Inject
    protected OidcClient client;

    @Override
    protected Authentication createInitialToken(PlayWebContext context) throws Exception {
        OidcCredentials credentials = (OidcCredentials)client.getCredentials(context);
        Logger.info("Check oidc secured: " + credentials);
        return new ClientAuthenticationToken(credentials, "OidcClient");
    }
}
