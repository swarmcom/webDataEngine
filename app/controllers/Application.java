package controllers;

import auth.AuthenticationAction;
import auth.FormAuthentication;
import auth.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.Play;
import play.mvc.*;

@Component
@With(AuthenticationAction.class)
@FormAuthentication
@Security.Authenticated(Secured.class)
@PreAuthorize("hasRole('ROLE_USER')")
public class Application extends Controller {

    public Result login() {
        return redirect("/users");
    }

    public Result asset(String file) {
        return Results.ok(Play.application().getFile(file), true);
    }

}

