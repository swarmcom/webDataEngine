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
import java.util.Set;

@Component
public class ElkAccountUsernamePasswordAuthenticator extends UsernamePasswordAuthenticator {

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

        Account account = accounts.get(0);

        if (!StringUtils.equals(account.getSuperadminUserName(), username)) {
            this.throwsException("Username does not match");
        }

        if (!StringUtils.equals(account.getSuperadminInitialPassword(), password)) {
            this.throwsException("Password does not match");
        }

        CommonProfile profile = new CommonProfile();
        profile.setId(username);
        profile.addAttribute("username", username);
        profile.addAttribute("accountid", currentAccountId);
        Set<String> accRoles = accounts.get(0).getRoles();

        for (String role : accRoles) {
            profile.addRole(role);
        }

        return profile;
    }
}

