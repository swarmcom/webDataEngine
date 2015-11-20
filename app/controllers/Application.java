package controllers;

import domain.User;
import play.mvc.Controller;
import play.mvc.Result;
import service.UserService;
import views.html.index;

import javax.inject.Inject;

public class Application extends Controller {
    @Inject
    UserService userService;

    public Result index() {
        userService.createUser("mirceaC", "baban");
        User user = userService.getUser("mirceaC");
        return ok(index.render("Your new application is ready." + user.getUserName()));
    }

}
