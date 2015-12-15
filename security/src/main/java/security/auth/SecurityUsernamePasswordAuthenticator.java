package security.auth;


import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.http.credentials.UsernamePasswordCredentials;
import org.pac4j.http.credentials.authenticator.UsernamePasswordAuthenticator;
import org.pac4j.http.profile.HttpProfile;

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

        HttpProfile profile = new HttpProfile();
        profile.setId(username);
        profile.addAttribute("username", username);
        credentials.setUserProfile(profile);
    }

    protected void throwsException(String message) {
        throw new CredentialsException(message);
    }
}
