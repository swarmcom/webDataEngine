package controllers;

import play.Play;
import play.mvc.*;

import org.pac4j.play.java.Secure;
import org.springframework.stereotype.Component;

@Component
public class Application extends Controller {

    @Secure(clients = "OidcClient", authorizers = "googleSuperadmin")
    public Result googleSuperadminAsset(String file) {
        return Results.ok(Play.application().getFile(file), true);
    }

    @Secure(clients = "FormClient", authorizers = "superadmin")
    public Result superadminAsset(String file) {
        return Results.ok(Play.application().getFile(file), true);
    }

    @Secure(clients = "FormClient", authorizers = "admin")
    public Result adminAsset(String file) {
        return Results.ok(Play.application().getFile(file), true);
    }

    @Secure(clients = "FormClient", authorizers = "user")
    public Result userAsset(String file) {
        return Results.ok(Play.application().getFile(file), true);
    }

    @Secure(clients = "FormClient", authorizers = "onlyUser")
    public Result onlyUserAsset(String file) {
        return Results.ok(Play.application().getFile(file), true);
    }
}