package security.validator;

import api.domain.Account;
import api.domain.BeanDomain;
import api.domain.User;
import api.service.AccountService;
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

import java.util.List;

@Component
public class AccountUsernamePasswordAuthenticator extends UsernamePasswordAuthenticator {

    @Inject
    UserService userService;

    @Inject
    AccountService accountService;

    @Inject
    private SecurityPasswordEncoder securityPasswordEncoder;

    @Override
    public CommonProfile validate(String username, String password, WebContext context) {
        String currentAccountId = context.getRequestParameter("accountid");
        List<Account> accounts = accountService.getAccountsByAccountName(currentAccountId);
        if (accounts == null || accounts.size() != 1) {
            this.throwsException("None or more accounts with account id: " + currentAccountId);
        }
        accountService.refreshTenantSpringContexts(accounts.get(0));
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

        return profile;
    }
}

