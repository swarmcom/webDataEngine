package security.authorizer;

import api.config.ApiConfig;
import org.pac4j.core.authorization.authorizer.ProfileAuthorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;
import play.Logger;

import java.util.List;

public class GoogleSuperadminAuthorizer extends ProfileAuthorizer<CommonProfile> {

    @Override
    protected boolean isProfileAuthorized(WebContext webContext, CommonProfile commonProfile) throws HttpAction {
        if (commonProfile != null) {
            String email =  getConfGoogleSuperadmin(commonProfile);
            if (email != null) {
                commonProfile.addRole("ROLE_SUPERADMIN");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAuthorized(WebContext webContext, List<CommonProfile> list) throws HttpAction {
        return isAnyAuthorized(webContext, list);
    }

    private String getConfGoogleSuperadmin(CommonProfile profile) {
        Boolean verified = (Boolean)profile.getAttribute("email_verified");
        String email = (String)profile.getAttribute("email");
        if (email == null) {
            return null;
        }
        Logger.info("Google email address: " + email);
        List<String> superadminList = ApiConfig.configuration.getStringList("superadmin.gmail");

        return verified && superadminList.contains(email) ? email : null;
    }
}
