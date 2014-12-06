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
        String fullName = session.get("fullName");
        String restaurantName = session.get("restaurantName");
        String restaurantUrl = session.get("restaurantUrl");
        String restaurantDescription = session.get("restaurantDescription");
        String restaurantSlogan = session.get("restaurantSlogan");
        return ok(index.render("Home", fullName, restaurantName, restaurantUrl, restaurantDescription, restaurantSlogan));
    }

}
