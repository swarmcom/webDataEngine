package managers;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.store.PlayCacheStore;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import play.Logger;
import play.mvc.Http;
import security.token.SecurityUsernamePasswordAuthenticationToken;

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

    public void saveOidcUserProfile(Http.Context ctx, ClientAuthenticationToken token) {
        UserProfile userProfile = token.getUserProfile();
        saveUserProfile(ctx, userProfile);
        //save roles as we cannot extend ClientAuthenticationProvider as we should to save roles in there
        for (GrantedAuthority authority : token.getAuthorities()) {
            userProfile.addRole(authority.getAuthority());
        }
    }

    public void saveFormUserProfile(Http.Context ctx, SecurityUsernamePasswordAuthenticationToken token) {
        UserProfile userProfile = token.getUserProfile();
        saveUserProfile(ctx, userProfile);
    }

    public UserProfile getUserProfile(WebContext context) {
        ProfileManager manager = new ProfileManager(context);

        return manager.isAuthenticated() ? (UserProfile)manager.get(true).get() : null;
    }

    public UserProfile getUserProfile(Http.Context ctx) {
        return getUserProfile(getProfileContext(ctx));
    }

    public WebContext getProfileContext(Http.Context context) {
        PlayWebContext webCtx = new PlayWebContext(context, dataStore);
        return webCtx;
    }
}
