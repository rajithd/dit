package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        Http.Session session = Http.Context.current().session();
        String username = session.get("username");
        if (username == null) {
            return redirect(routes.LoginController.login());
        }
        String firstName = session.get("firstName");
//        String userId = session("userId");
        return ok(index.render("Home", firstName));
    }

}
