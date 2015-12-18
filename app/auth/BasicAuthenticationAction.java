package auth;

import managers.AppTokenManager;
import org.pac4j.play.java.RequiresAuthentication;
import org.springframework.security.core.Authentication;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

public class BasicAuthenticationAction extends AuthenticationAction {

    @Inject
    private AppTokenManager appTokenManager;

    @Override
    @RequiresAuthentication(clientName = "DirectBasicAuthClient")
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        Authentication token = createInitialToken(context);
        appTokenManager.applyInitialToken(context, token);

        return delegate.call(context);
    }

    protected Authentication createInitialToken(Http.Context context) throws Exception {
        return appTokenManager.createBasicInitialToken(context);
    }
}


