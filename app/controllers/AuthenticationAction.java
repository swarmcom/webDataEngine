package controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import play.Logger;
import play.cache.CacheApi;
import play.cache.NamedCache;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import security.util.MD5Encoder;

import javax.inject.Inject;

public abstract class AuthenticationAction<T> extends Action<T> {

    @Inject
    @NamedCache("session-cache")
    protected CacheApi sessionCache;


    protected void createAuthenticationTokenInSession(Http.Context context, String userName, String password) {
        context.session().put("username", userName);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
        sessionCache.set(userName, token);
        context.session().clear();
        context.session().put("username", userName);
    }

    protected void setRequestAuthenticationToken(String userName, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
