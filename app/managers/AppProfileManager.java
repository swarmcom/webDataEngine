package managers;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.store.PlayCacheStore;
import org.springframework.stereotype.Component;
import play.mvc.Http;

import javax.inject.Inject;

@Component
public class AppProfileManager {

    @Inject
    protected PlayCacheStore dataStore;

    public void saveUserProfile(Http.Context ctx, UserProfile userProfile) {
        if (userProfile == null) {
            return;
        }

        PlayWebContext context = new PlayWebContext(ctx, this.dataStore);
        ProfileManager manager = new ProfileManager(context);
        if(userProfile != null) {
            manager.save(true, (CommonProfile)userProfile, false);
        }
    }

    public UserProfile getAuthenticatedUserProfile(WebContext context) {
        ProfileManager manager = new ProfileManager(context);

        return manager.isAuthenticated() ? (UserProfile)manager.get(true).get() : null;
    }

    public UserProfile getAuthenticatedUserProfile(Http.Context ctx) {
        return getAuthenticatedUserProfile(getProfileContext(ctx));
    }

    public WebContext getProfileContext(Http.Context context) {
        PlayWebContext webCtx = new PlayWebContext(context, dataStore);
        return webCtx;
    }

    public String getSessionAccountId(Http.Context ctx) {
        UserProfile profile = getAuthenticatedUserProfile(ctx);
        return (String) profile.getAttribute("accountid");
    }

    public String getSessionProviderId(Http.Context ctx) {
        UserProfile profile = getAuthenticatedUserProfile(ctx);
        return (String) profile.getAttribute("providerid");
    }
}
