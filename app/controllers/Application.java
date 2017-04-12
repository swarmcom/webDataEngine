package controllers;

import play.Logger;
import play.Play;
import play.mvc.*;

import org.pac4j.play.java.Secure;
import org.springframework.stereotype.Component;

@Component
public class Application extends Controller {

    @Secure(clients = "OidcClient", authorizers = "googleSuperadmin")
    public Result googleSuperadminAsset(String file) {
        return Results.ok(Play.application().resourceAsStream(file)).as(Http.MimeTypes.HTML);
    }

    @Secure(clients = "FormClient", authorizers = "superadmin")
    public Result superadminAsset(String file) {
        return Results.ok(Play.application().resourceAsStream(file)).as(Http.MimeTypes.HTML);
    }

    @Secure(clients = "formClientAccount", authorizers = "admin")
    public Result adminAssetAccount(String file) {
        return Results.ok(Play.application().resourceAsStream(file)).as(Http.MimeTypes.HTML);
    }

    @Secure(clients = "formClientProvider", authorizers = "provider")
    public Result adminAssetProvider(String file) {
        return Results.ok(Play.application().resourceAsStream(file)).as(Http.MimeTypes.HTML);
    }

    @Secure(clients = "FormClient", authorizers = "user")
    public Result userAsset(String file) {
        return Results.ok(Play.application().resourceAsStream(file)).as(Http.MimeTypes.HTML);
    }

    @Secure(clients = "FormClient", authorizers = "onlyUser")
    public Result onlyUserAsset(String file) {
        return Results.ok(Play.application().resourceAsStream(file)).as(Http.MimeTypes.HTML);
    }
}