package controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.Play;
import play.mvc.*;

@Component
@FormAuthentication
@Security.Authenticated(controllers.Secured.class)
@PreAuthorize("hasRole('ROLE_USER')")
public class Application extends Controller {

    public Result login() {
        return redirect("/users");
    }

    public Result asset(String file) {
        return Results.ok(Play.application().getFile(file), true);
    }

}

