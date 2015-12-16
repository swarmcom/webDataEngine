package auth;

import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.store.DataStore;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;

public abstract class Secured extends Security.Authenticator {
    @Inject
    protected DataStore dataStore;

    public String getUsername(Http.Context ctx) {
        try {
            String token = applyInitialToken(ctx);
            Logger.info("InitialToken: " + token);
            return token;
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

    protected String applyInitialToken(Http.Context ctx) throws Exception {
        PlayWebContext context = new PlayWebContext(ctx, dataStore);
        Authentication initialToken = createInitialToken(context);
        SecurityContextHolder.getContext().setAuthentication(initialToken);
        return initialToken.toString();
    }

    protected abstract Authentication createInitialToken(PlayWebContext context) throws Exception;

    protected UserProfile getUserProfile(PlayWebContext context) {
        ProfileManager manager = new ProfileManager(context);
        return manager.get(true);
    }
}
