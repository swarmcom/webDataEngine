package auth;

import org.springframework.security.core.Authentication;
import play.mvc.Http;

public class OidcSecured extends Secured {

    @Override
    protected Authentication createInitialToken(Http.Context context) throws Exception {
        return appTokenManager.createOidcInitialToken(context);
    }
}
