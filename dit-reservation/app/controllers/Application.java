package controllers;

import com.dit.Restaurant;
import com.dit.security.OAuth2Token;
import play.Logger;
import play.cache.Cache;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import util.HttpConnector;
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
        Restaurant rest = (Restaurant) Cache.get("searchResult");
        return ok(restaurant.render("Search", null, rest));
    }


    public static Result search() throws Exception {
        DynamicForm dynamicForm = form().bindFromRequest();
        Logger.info("Date is: " + dynamicForm.get("reservedDate"));
        Logger.info("restaurant is: " + dynamicForm.get("restaurant"));

        HttpConnector httpConnector = new HttpConnector();
        Restaurant restaurant = httpConnector.doGet("http://localhost:8080/dit-api/public/admin/restaurant/" +  dynamicForm.get("restaurant"), Restaurant.class);
        Cache.set("searchResult", restaurant, 3600 * 60 * 60);
        return redirect(routes.Application.restaurantView());

    }

    public static Result logout() {
        Cache.set("token", null);
        return redirect(routes.Application.index());
    }

    public static Result reservationSubmit(){
        OAuth2Token token = (OAuth2Token) Cache.get("token");
        Restaurant rest = (Restaurant) Cache.get("searchResult");
        return redirect(routes.Application.index());
    }
}
