package auth;

import managers.AppTokenManager;
import play.Logger;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

public class AuthenticationAction extends Action.Simple {

    @Inject
    private AppTokenManager appTokenManager;

    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        Logger.info("Filtering request via authentication Layer");
        appTokenManager.clearRequestAuthenticationToken();
        return delegate.call(context);
    }

}
