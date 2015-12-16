package auth;

import org.springframework.security.core.context.SecurityContextHolder;
import play.Logger;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class AuthenticationAction extends Action.Simple {

    private void clearRequestAuthenticationToken() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        Logger.info("Filtering request via authentication Layer");
        clearRequestAuthenticationToken();
        return delegate.call(context);
    }

}
