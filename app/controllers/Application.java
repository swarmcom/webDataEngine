package controllers;

import auth.OidcSecured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.Play;
import play.mvc.*;

@Component
@Security.Authenticated(OidcSecured.class)
@PreAuthorize("hasRole('ROLE_USER')")
public class Application extends Controller {

    public Result asset(String file) {
        return Results.ok(Play.application().getFile(file), true);
    }
}

