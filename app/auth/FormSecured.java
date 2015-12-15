package auth;

import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.http.credentials.UsernamePasswordCredentials;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.credentials.OidcCredentials;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.store.DataStore;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import security.auth.SecurityUsernamePasswordAuthenticationToken;
import security.auth.SessionAuthenticationToken;

import javax.inject.Inject;

public class FormSecured extends Security.Authenticator {
    @Inject
    protected DataStore dataStore;
    @Inject
    protected FormClient client;

    public String getUsername(Http.Context ctx) {
        clearRequestAuthenticationToken();
        PlayWebContext context = new PlayWebContext(ctx, dataStore);
        UserProfile profile = getUserProfile(context);
        if (profile != null) {
            SessionAuthenticationToken sessionToken = new SessionAuthenticationToken(profile);
            SecurityContextHolder.getContext().setAuthentication(sessionToken);
            Logger.info("Authenticated session " + sessionToken.getPrincipal());
            return (String)sessionToken.getPrincipal();
        }
        try {
            UsernamePasswordCredentials credentials = (UsernamePasswordCredentials)client.getCredentials(context);
            SecurityUsernamePasswordAuthenticationToken initialToken = createFormToken(credentials);
            SecurityContextHolder.getContext().setAuthentication(initialToken);
            return credentials.getUsername();
        } catch(Exception ex) {
            Logger.info("Cannot create initial Oidc token", ex);
            return null;
        }
    }

    private ClientAuthenticationToken createOidcToken(OidcCredentials credentials, String clientName) {
        return new ClientAuthenticationToken(credentials, clientName);
    }

    private SecurityUsernamePasswordAuthenticationToken createFormToken(UsernamePasswordCredentials credentials) {
        return new SecurityUsernamePasswordAuthenticationToken(credentials);
    }

    private SessionAuthenticationToken createSessionToken(UserProfile profile) {
        return new SessionAuthenticationToken(profile);
    }

    private void clearRequestAuthenticationToken() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public Result onUnauthorized(Http.Context ctx) {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        if (token != null) {
            return unauthorized("unauthorized");
        } else {
            return redirect("/");
        }
    }

    private UserProfile getUserProfile(PlayWebContext context) {
        ProfileManager manager = new ProfileManager(context);
        return manager.get(true);
    }
}
