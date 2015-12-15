package controllers;


import auth.FormSecured;
import auth.OidcSecured;
import org.pac4j.core.config.Config;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.RequiresAuthentication;

import org.pac4j.play.store.DataStore;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.auth.SecurityUsernamePasswordAuthenticationToken;

import javax.inject.Inject;
import java.util.Collection;

@Component
public class Authenticator extends Controller {
    @Inject
    protected Config config;

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

    @Security.Authenticated(OidcSecured.class)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result callback() {
        ClientAuthenticationToken token = (ClientAuthenticationToken) SecurityContextHolder.
                getContext().getAuthentication();
        // save user profile
        if (token != null) {
            saveUserProfile(token);
            return oidcIndex();
        }
        return redirect("/oidc");
    }

    @Security.Authenticated(FormSecured.class)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result login() {
        SecurityUsernamePasswordAuthenticationToken token = (SecurityUsernamePasswordAuthenticationToken) SecurityContextHolder.
                getContext().getAuthentication();
        //save user profile
        if (token != null) {
            saveUserProfile(token);
            return formIndex();
        }
        return redirect("/");
    }

    public Result form() {
        return redirect("/");
    }

    private void saveUserProfile(ClientAuthenticationToken token) {
        UserProfile userProfile = token.getUserProfile();
        if (userProfile == null) {
            return;
        }
        Collection<GrantedAuthority> grantedAuthorities = token.getAuthorities();
        for (GrantedAuthority authority : grantedAuthorities) {
            userProfile.addRole(authority.getAuthority());
        }
        PlayWebContext context = new PlayWebContext(ctx(), this.dataStore);
        ProfileManager manager = new ProfileManager(context);
        if(userProfile != null) {
            manager.save(true, userProfile);
        }
    }

    private void saveUserProfile(SecurityUsernamePasswordAuthenticationToken token) {
        UserProfile userProfile = token.getUserPwdCredentials().getUserProfile();
        if (userProfile == null) {
            return;
        }
        Collection<GrantedAuthority> grantedAuthorities = token.getAuthorities();
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