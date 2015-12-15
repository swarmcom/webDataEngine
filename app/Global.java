import org.pac4j.core.authorization.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oidc.client.OidcClient;
import play.Application;
import play.GlobalSettings;
import play.Logger;

import play.Play;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import api.service.UserService;

import javax.inject.Inject;
import java.lang.reflect.Method;

public class Global extends GlobalSettings {

    public Action onRequest(Http.Request request, Method actionMethod) {
        return new Action.Simple() {
            public F.Promise<Result> call(Http.Context ctx) throws Throwable {
                String userName = null;
                try {
                    userName = request.username();
                    Logger.info("Username in session: "+ userName);
                    return delegate.call(ctx);
                } catch (Exception ex) {
                    Logger.info("Cannot Call Action for reason: " + ex.getMessage(), ex);
                    F.Promise<Result> result = null;
                    if (userName != null) {
                        Logger.info("Redirect to login page");
                        result = F.Promise.promise(() -> redirect("/"));
                    } else {
                        result = F.Promise.promise(() ->  unauthorized("unauthorized"));
                    }
                    return result;
                }
            }
        };
    }
}
