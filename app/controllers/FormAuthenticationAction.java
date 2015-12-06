package controllers;

import domain.User;
import models.domain.ModelUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import play.Logger;
import play.cache.CacheApi;
import play.cache.NamedCache;
import play.data.Form;
import play.libs.F;
import play.mvc.*;
import security.util.MD5Encoder;
import service.UserService;

import javax.inject.Inject;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class FormAuthenticationAction extends AuthenticationAction<FormAuthentication> {

    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        SecurityContextHolder.getContext().setAuthentication(null);
        Form<ModelUser> userForm = Form.form(ModelUser.class);
        ModelUser user = userForm.bindFromRequest().get();
        String userName = user.getUserName();
        if (!isEmpty(userName)) {
            createAuthenticationTokenInSession(context, userName, user.getPassword());
            Logger.info("FORM Authentication!!");
        } else {
            Logger.info("No FORM Authentication detected");
        }

        return delegate.call(context);
    }
}
