package controllers;


import auth.AuthenticationAction;
import auth.FormSecured;
import auth.OidcSecured;
import auth.SessionSecured;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.RequiresAuthentication;

import org.pac4j.play.store.DataStore;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import play.Play;
import play.mvc.*;
import security.token.SecurityUsernamePasswordAuthenticationToken;

import javax.inject.Inject;
import java.util.Collection;

@Component
public class Application extends Controller {

    @Inject
    protected DataStore dataStore;

    @RequiresAuthentication(clientName = "OidcClient")
    public Result oidcIndex() {
        return redirect("/users");
    }

    @RequiresAuthentication(clientName = "FormClient")
    public Result formIndex() {
        return redirect("/users");
    }

    @With(AuthenticationAction.class)
    @Security.Authenticated(OidcSecured.class)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result callback() {
        ClientAuthenticationToken token = (ClientAuthenticationToken) SecurityContextHolder.
                getContext().getAuthentication();
        // save user profile
        if (token != null) {
            saveOidcUserProfile(token);
            return oidcIndex();
        }
        return redirect("/oidc");
    }

    @With(AuthenticationAction.class)
    @Security.Authenticated(FormSecured.class)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result login() {
        SecurityUsernamePasswordAuthenticationToken token = (SecurityUsernamePasswordAuthenticationToken) SecurityContextHolder.
                getContext().getAuthentication();
        //save user profile
        if (token != null) {
            saveFormUserProfile(token);
            return formIndex();
        }
        return redirect("/");
    }

    public Result form() {
        return redirect("/");
    }

    @With(AuthenticationAction.class)
    @Security.Authenticated(SessionSecured.class)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result asset(String file) {
        return Results.ok(Play.application().getFile(file), true);
    }

    private void saveOidcUserProfile(ClientAuthenticationToken token) {
        UserProfile userProfile = token.getUserProfile();
        saveUserProfile(userProfile, token);
    }

    private void saveFormUserProfile(SecurityUsernamePasswordAuthenticationToken token) {
        UserProfile userProfile = token.getUserPwdCredentials().getUserProfile();
        saveUserProfile(userProfile, token);

    }

    private void saveUserProfile(UserProfile userProfile, Authentication token) {
        if (userProfile == null) {
            return;
        }
        Collection<? extends GrantedAuthority> grantedAuthorities = token.getAuthorities();
        for (GrantedAuthority authority : grantedAuthorities) {
            userProfile.addRole(authority.getAuthority());
        }
        PlayWebContext context = new PlayWebContext(ctx(), this.dataStore);
        ProfileManager manager = new ProfileManager(context);
        if(userProfile != null) {
            manager.save(true, userProfile);
        }
    }
}