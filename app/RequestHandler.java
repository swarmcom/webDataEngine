import play.Logger;
import play.http.HttpRequestHandler;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;

public class RequestHandler implements HttpRequestHandler {

    @Override
    public Action createAction(Http.Request req, Method method) {
        return new Action.Simple() {
            @Override
            public F.Promise<Result> call(Http.Context ctx) throws Throwable {
                try {
                    return delegate.call(ctx);
                } catch (Exception ex) {
                    Logger.info("Cannot Call Action for reason: " + ex.getMessage(), ex);
                    return F.Promise.promise(() ->  unauthorized("unauthorized"));
                }
            }
        };
    }

    @Override
    public Action wrapAction(Action action) {
        return action;
    }
}


