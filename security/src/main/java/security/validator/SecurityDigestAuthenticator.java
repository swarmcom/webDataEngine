package security.validator;

import api.domain.User;
import api.service.UserService;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.http.credentials.DigestCredentials;

import org.springframework.stereotype.Component;
import play.Logger;
import security.encoder.DigestPasswordEncoder;

import javax.inject.Inject;

@Component
public class SecurityDigestAuthenticator implements Authenticator<DigestCredentials> {

    @Inject
    UserService userService;

    @Inject
    private DigestPasswordEncoder passwordEncoder;

    @Override
    public void validate(DigestCredentials credentials, WebContext context) {
        if (credentials == null) {
            this.throwsException("No credential");
        }

        String username = credentials.getUsername();
        String clientResponse = credentials.getToken();
        if (CommonHelper.isBlank(username)) {
            this.throwsException("Username cannot be blank");
        }

        if (CommonHelper.isBlank(clientResponse)) {
            this.throwsException("Client response cannot be blank");
        }

        String currentAccountId = context.getRequestParameter("accountid");
        User user = userService.getUser(currentAccountId, username);

        if (user == null) {
            this.throwsException("User not found");
        }
        String userPassword = user.getPassword();
        String serverResponse = passwordEncoder.getServerResponse(userPassword, credentials);
        if(!this.passwordEncoder.matches(clientResponse, serverResponse)) {
            Logger.info("Authentication failed: digest token from server does not match");
            this.throwsException("Bad credentials");
        }
        CommonProfile profile = new CommonProfile();
        profile.setId(username);
        profile.addAttribute("username", username);
        profile.addAttribute("accountid", currentAccountId);
        for (String role : user.getRoles()) {
            profile.addRole(role);
        }

        credentials.setUserProfile(profile);
    }
    protected void throwsException(String message) {
        throw new CredentialsException(message);
    }
}
