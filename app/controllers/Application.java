package controllers;


import auth.*;
import managers.AppProfileManager;

import org.pac4j.play.java.Secure;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import play.Play;
import play.mvc.*;
import security.token.SecurityUsernamePasswordAuthenticationToken;

import javax.inject.Inject;

@Component
public class Application extends Controller {

    @Inject
    AppProfileManager appProfileManager;

    @Secure(clients = "OidcClient")
    public Result oidcIndex() {
        return redirect("/main/config");
    }

    @Secure(clients = "FormClient")
    public Result formIndex() {
        return redirect("/account");
    }

    @With(AuthenticationAction.class)
    @Security.Authenticated(OidcSecured.class)
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public Result callback() {
        ClientAuthenticationToken token = (ClientAuthenticationToken) SecurityContextHolder.
                getContext().getAuthentication();
        // save user profile
        if (token != null) {
            appProfileManager.saveOidcUserProfile(ctx(), token);
            return oidcIndex();
        }
        return redirect("/main/oidc");
    }

    @With(AuthenticationAction.class)
    @Security.Authenticated(FormSecured.class)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result login() {
        SecurityUsernamePasswordAuthenticationToken token = (SecurityUsernamePasswordAuthenticationToken) SecurityContextHolder.
                getContext().getAuthentication();
        //save user profile
        if (token != null) {
            appProfileManager.saveFormUserProfile(ctx(), token);
            return formIndex();
        }
        return form();
    }

    public Result form() {
        return redirect("/");
    }

    @With(AuthenticationAction.class)
    @Security.Authenticated(SessionSecured.class)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SUPERADMIN')")
    public Result asset(String file) {
        return Results.ok(Play.application().getFile(file), true);
    }
}