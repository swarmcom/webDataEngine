package auth;

import managers.AppProfileManager;
import managers.AppTokenManager;
import org.pac4j.core.profile.UserProfile;
import org.springframework.security.core.Authentication;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;

public abstract class Secured extends Security.Authenticator {

    @Inject
    protected AppProfileManager appProfileManager;

    @Inject
    protected AppTokenManager appTokenManager;

    public String getUsername(Http.Context ctx) {
        try {
            Authentication token = retrieveExistingToken();
            if (token == null) {
                token = appTokenManager.applyInitialToken(ctx, createInitialToken(ctx));
            }
            Logger.info("InitialToken: " + token);
            if (token != null) {
                return appTokenManager.getUsernameFromToken(token);
            }
            return null;
        } catch(Exception ex) {
            Logger.info("Cannot apply initial token", ex);
            return null;
        }
    }

    public Result onUnauthorized(Http.Context ctx) {
        Logger.info("Unauthorized from: " + this.getClass().getName());
        return unauthorized("unauthorized");

    }

    protected Authentication retrieveExistingToken() {
        return appTokenManager.retrieveExistingToken();
    }

    protected abstract Authentication createInitialToken(Http.Context context) throws Exception;

    protected UserProfile getUserProfile(Http.Context context) {
        return appProfileManager.getUserProfile(context);
    }
}
