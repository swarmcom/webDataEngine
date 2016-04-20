package security.validator;

import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.credentials.authenticator.UsernamePasswordAuthenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;


public class SecurityUsernamePasswordAuthenticator implements UsernamePasswordAuthenticator {

    public void validate(UsernamePasswordCredentials credentials) {
        if (credentials == null) {
            this.throwsException("No credential");
        }

        String username = credentials.getUsername();
        String password = credentials.getPassword();
        if (CommonHelper.isBlank(username)) {
            this.throwsException("Username cannot be blank");
        }

        if (CommonHelper.isBlank(password)) {
            this.throwsException("Password cannot be blank");
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

