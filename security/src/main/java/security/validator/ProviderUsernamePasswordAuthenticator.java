package security.validator;

import api.domain.Provider;
import api.service.ProviderService;
import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.stereotype.Component;
import play.Logger;
import security.authorizer.RoleType;

import javax.inject.Inject;

@Component
public class ProviderUsernamePasswordAuthenticator extends UsernamePasswordAuthenticator {

    @Inject
    ProviderService providerService;

    @Override
    public CommonProfile validate(String username, String password, WebContext context) {
        String currentProviderId = context.getRequestParameter("providerid");
        Logger.info("MIRCEA " + currentProviderId);
        Provider provider = providerService.getProvider(currentProviderId);

        if (provider == null) {
            this.throwsException("Provider not found");
        }

        if (!StringUtils.equals(password, provider.getSuperadminPassword())) {
            this.throwsException("Password does not match");
        }
        CommonProfile profile = new CommonProfile();
        profile.setId(username);
        profile.addAttribute("provideradmin", username);
        profile.addAttribute("providerid", currentProviderId);
        profile.addRole(RoleType.ROLE_PROVIDER.name());
        return profile;
    }
}
