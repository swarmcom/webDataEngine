package controllers;

import domain.User;
import models.domain.ModelUser;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import play.Play;
import play.data.Form;
import play.mvc.*;
import service.UserService;

import javax.inject.Inject;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
@FormAuthentication
@BasicAuthentication
@Security.Authenticated(controllers.Secured.class)
@Secured("ROLE_USER")
@PreAuthorize("true")
public class Application extends Controller {

    public Result login() {
        return redirect("/users");
    }

    public Result asset(String file) {
        return Results.ok(Play.application().getFile(file), true);
    }

}

