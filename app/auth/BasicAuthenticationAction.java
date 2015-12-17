package auth;

import managers.AppProfileManager;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.credentials.UsernamePasswordCredentials;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.RequiresAuthentication;
import org.pac4j.play.store.DataStore;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import play.Logger;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import api.service.UserService;
import security.token.ClientType;
import security.token.SecurityUsernamePasswordAuthenticationToken;

import javax.inject.Inject;

import static org.apache.commons.lang3.StringUtils.isEmpty;


public class BasicAuthenticationAction extends AuthenticationAction {
    @Inject
    DirectBasicAuthClient basicClient;

    @Inject
    protected DataStore dataStore;

    @Inject
    AppProfileManager appProfileManager;

    @Override
    @RequiresAuthentication(clientName = "DirectBasicAuthClient")
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        PlayWebContext webCtx = new PlayWebContext(context, dataStore);
        UsernamePasswordCredentials credentials = basicClient.getCredentials(webCtx);
        if (credentials != null) {
            Authentication token = applyInitialToken(context);
        }

        return delegate.call(context);
    }

    protected Authentication createInitialToken(PlayWebContext context) throws Exception {
        UsernamePasswordCredentials credentials = (UsernamePasswordCredentials)basicClient.getCredentials(context);
        Logger.info("Check form basic " + credentials);
        return new SecurityUsernamePasswordAuthenticationToken(credentials, ClientType.DirectBasicAuthClient);
    }

    protected Authentication applyInitialToken(Http.Context ctx) throws Exception {
        PlayWebContext context = new PlayWebContext(ctx, dataStore);
        Authentication initialToken = createInitialToken(context);
        Logger.info("Apply initial token BASIC " + initialToken);
        SecurityContextHolder.getContext().setAuthentication(initialToken);
        return initialToken;
    }
}


