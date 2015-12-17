package auth;

import managers.AppProfileManager;
import managers.AppTokenManager;
import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oidc.credentials.OidcCredentials;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.store.DataStore;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import security.token.ClientToken;
import security.token.ClientType;

import javax.inject.Inject;

public abstract class Secured extends Security.Authenticator {
    @Inject
    protected DataStore dataStore;

    @Inject
    AppProfileManager appProfileManager;

    @Inject
    AppTokenManager appTokenManager;

    public String getUsername(Http.Context ctx) {
        try {
            Authentication token = retrieveExistingToken();
            if (token == null) {
                PlayWebContext context = new PlayWebContext(ctx, dataStore);
                token = appTokenManager.applyInitialToken(ctx, createInitialToken(context));
            }
            Logger.info("InitialToken: " + token);
            return appTokenManager.getUsernameFromToken(token);
        } catch(Exception ex) {
            Logger.info("Cannot apply initial token", ex);
            return null;
        }
    }

    public Result onUnauthorized(Http.Context ctx) {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        if (token != null) {
            return unauthorized("unauthorized");
        } else {
            return redirect("/");
        }
    }

    protected Authentication retrieveExistingToken() {
        return appTokenManager.retrieveExistingToken();
    }

    protected abstract Authentication createInitialToken(PlayWebContext context) throws Exception;

    protected UserProfile getUserProfile(PlayWebContext context) {
        return appProfileManager.getUserProfile(context);
    }
}
