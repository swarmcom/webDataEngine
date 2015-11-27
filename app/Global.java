import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import play.GlobalSettings;
import play.mvc.Action;
import play.mvc.Http;

import java.lang.reflect.Method;

public class Global extends GlobalSettings {

    @Override
    public Action onRequest(Http.Request request, Method actionMethod) {
        UsernamePasswordAuthenticationToken req = new UsernamePasswordAuthenticationToken("user", "user");
        SecurityContextHolder.getContext().setAuthentication(req);
        return super.onRequest(request, actionMethod);
    }
}
