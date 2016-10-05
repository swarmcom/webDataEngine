package security.validator;

import api.domain.User;
import api.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;

import play.Logger;
import security.encoder.SecurityPasswordEncoder;
import security.util.EncoderUtil;

import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component
public class SecurityUsernamePasswordAuthenticator implements Authenticator<UsernamePasswordCredentials> {

    @Inject
    UserService userService;

    @Inject
    private SecurityPasswordEncoder securityPasswordEncoder;

    public void validate(UsernamePasswordCredentials credentials, WebContext context) {
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

        String currentAccountId = context.getRequestParameter("accountid");
        User user = userService.getUser(currentAccountId, username);

        if (user == null) {
            this.throwsException("User not found");
        }

        if (!this.securityPasswordEncoder.matches(password, user.getPassword()) &&
                !StringUtils.equals(EncoderUtil.digestEncodePassword(username, EncoderUtil.DIGEST_REALM, password), user.getPassword())) {
            this.throwsException("Password does not match");
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

