package auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import play.Logger;
import play.cache.CacheApi;
import play.cache.NamedCache;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.html.defaultpages.unauthorized;

import javax.inject.Inject;


public class Secured extends Security.Authenticator {
    @Inject
    @NamedCache("session-cache")
    protected CacheApi sessionCache;

    public String getUsername(Http.Context ctx) {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        if (token != null) {
            String principal = token.getPrincipal().toString();
            Logger.info("Token already created in request " + principal);
            return principal;
        }
        //check if username in session - recreate spring token if found
        String userName = (String)ctx.session().get("username");
        Logger.info("Username in session (Secured): " + userName);
        if (userName != null) {
            token = sessionCache.get(userName);
            Logger.info("Token: " + token);
            if (token != null) {
                //Session token already created in session cache, make sure request is aware of it
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        } else {
            Logger.info("Session expired ");
        }
        return userName;
    }

    public Result onUnauthorized(Http.Context ctx) {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        if (token != null) {
            return unauthorized("unauthorized");
        } else {
            return redirect("/");
        }
    }
}
