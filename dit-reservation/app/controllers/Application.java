package controllers;

import com.dit.security.OAuth2Token;
import play.Logger;
import play.cache.Cache;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.restaurant;

import static play.data.Form.form;

public class Application extends Controller {

    public static Result index() {
        OAuth2Token token = (OAuth2Token) Cache.get("token");
        if (token == null) {
            return ok(index.render("Home", null));
        } else {
            return ok(index.render("Home", token.getUser()));
        }
    }

    public static Result restaurantView() {
//        OAuth2Token token = (OAuth2Token) Cache.get("token");
        return ok(restaurant.render("Search", null));
    }


    public static Result search() {
        DynamicForm dynamicForm = form().bindFromRequest();
        Logger.info("Date is: " + dynamicForm.get("reservedDate"));
        Logger.info("restaurant is: " + dynamicForm.get("restaurant"));
        return redirect(routes.Application.restaurantView());

    }
}
