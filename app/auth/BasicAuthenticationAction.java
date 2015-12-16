package auth;

import org.springframework.security.core.context.SecurityContextHolder;
import play.Logger;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import api.service.UserService;

import javax.inject.Inject;

import static org.apache.commons.lang3.StringUtils.isEmpty;


public class BasicAuthenticationAction extends AuthenticationAction {

    private static final String AUTHORIZATION = "authorization";
    private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String REALM = "Basic realm=\"webDataEngine\"";

    @Inject
    UserService userService;

    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        String authHeader = context.request().getHeader(AUTHORIZATION);
        if (authHeader == null) {
            Logger.info("No Basic authentication detected - move to next authenticator");
            return delegate.call(context);
        }

        String auth = authHeader.substring(6);
        byte[] decodedAuth = new sun.misc.BASE64Decoder().decodeBuffer(auth);
        String[] credString = new String(decodedAuth, "UTF-8").split(":");

        if (credString != null && credString.length == 2) {
            String userName = credString[0];
            String password = credString[1];
            if (!isEmpty(userName)) {
                Logger.info("BASIC Authentication !!");
                //createRequestAuthenticationToken(userName, password);
            }
        }
        return delegate.call(context);
    }
}


