package auth;

import models.domain.ModelUser;
import org.springframework.security.core.context.SecurityContextHolder;
import play.Logger;
import play.data.Form;
import play.libs.F;
import play.mvc.*;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class FormAuthenticationAction extends AuthenticationAction {

    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        Form<ModelUser> userForm = Form.form(ModelUser.class);
        ModelUser user = userForm.bindFromRequest().get();
        String userName = user.getUserName();
        Logger.info("FORM USERNAME " + userName);
        if (!isEmpty(userName)) {
            createAuthenticationTokenInSession(context, userName, user.getPassword());
            Logger.info("FORM Authentication!!");
        } else {
            Logger.info("No FORM Authentication detected");
        }

        return delegate.call(context);
    }
}
