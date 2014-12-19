package controllers;

import com.dit.security.OAuth2Token;
import play.cache.Cache;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        OAuth2Token token = (OAuth2Token) Cache.get("token");
        if (token == null) {
            return ok(index.render("Home", null));
        } else {
            return ok(index.render("Home", token.getUser()));
        }
    }
}
