package auth;

import org.pac4j.core.profile.UserProfile;

import org.pac4j.play.PlayWebContext;

import org.springframework.security.core.Authentication;

import play.Logger;
import security.token.SessionAuthenticationToken;

public class SessionSecured extends Secured {

    @Override
    protected Authentication createInitialToken(PlayWebContext context) throws Exception {
        UserProfile profile = getUserProfile(context);
        Logger.info("Check session secured " + profile);
        return new SessionAuthenticationToken(profile);
    }
}
