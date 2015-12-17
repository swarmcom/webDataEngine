package auth;

import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.http.credentials.UsernamePasswordCredentials;
import org.pac4j.play.PlayWebContext;
import org.springframework.security.core.Authentication;
import play.Logger;
import security.token.ClientType;
import security.token.SecurityUsernamePasswordAuthenticationToken;

import javax.inject.Inject;

public class FormSecured extends Secured {
    @Inject
    protected FormClient client;

    @Override
    protected Authentication createInitialToken(PlayWebContext context) throws Exception {
        UsernamePasswordCredentials credentials = (UsernamePasswordCredentials)client.getCredentials(context);
        Logger.info("Check form secured " + credentials);
        return new SecurityUsernamePasswordAuthenticationToken(credentials, ClientType.FormClient);
    }
}
