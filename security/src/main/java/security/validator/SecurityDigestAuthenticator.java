package security.validator;

import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.authenticator.TokenAuthenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.http.credentials.DigestCredentials;


public class SecurityDigestAuthenticator implements TokenAuthenticator {

    @Override
    public void validate(TokenCredentials credentials) {
        if (credentials == null) {
            this.throwsException("No credential");
        }
        DigestCredentials digestCredentials = (DigestCredentials)credentials;
        String username = digestCredentials.getUsername();
        if (CommonHelper.isBlank(username)) {
            this.throwsException("Username cannot be blank");
        }

        CommonProfile profile = new CommonProfile();
        profile.setId(username);
        profile.addAttribute("username", username);
        credentials.setUserProfile(profile);
    }
    protected void throwsException(String message) {
        throw new CredentialsException(message);
    }
}
