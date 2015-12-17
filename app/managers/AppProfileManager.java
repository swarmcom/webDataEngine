package managers;

import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.store.DataStore;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import play.mvc.Http;
import security.token.SecurityUsernamePasswordAuthenticationToken;

import javax.inject.Inject;
import java.util.Collection;

@Component
public class AppProfileManager {

    @Inject
    protected DataStore dataStore;

    private void saveUserProfile(Http.Context ctx, UserProfile userProfile) {
        if (userProfile == null) {
            return;
        }

        PlayWebContext context = new PlayWebContext(ctx, this.dataStore);
        ProfileManager manager = new org.pac4j.core.profile.ProfileManager(context);
        if(userProfile != null) {
            manager.save(true, userProfile);
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
        UserProfile userProfile = token.getUserPwdCredentials().getUserProfile();
        saveUserProfile(ctx, userProfile);
    }

    public UserProfile getUserProfile(PlayWebContext context) {
        ProfileManager manager = new ProfileManager(context);
        return manager.get(true);
    }

    public UserProfile getUserProfile(Http.Context ctx) {
        PlayWebContext context = new PlayWebContext(ctx, dataStore);
        return getUserProfile(context);
    }
}
