package auth;

import managers.AppProfileManager;
import org.pac4j.core.profile.UserProfile;
import play.Logger;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

public class SessionAuthenticatedAction extends Action.Simple {

    @Inject
    protected AppProfileManager appProfileManager;

    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        UserProfile profile = appProfileManager.getAuthenticatedUserProfile(context);
        return profile == null ? delegate.call(context) : delegate.delegate.call(context);
    }

}
