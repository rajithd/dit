package controllers;

import com.dit.Menu;
import com.dit.MenuItem;
import com.dit.Restaurant;
import com.dit.security.OAuth2Token;
import play.Logger;
import play.cache.Cache;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import util.HttpConnector;
import views.html.index;
import views.html.reservation;
import views.html.restaurant;
import views.html.sucess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
        List<Menu> menus = rest.getMenus();
        List<Menu> specialOffer = new ArrayList<>();
        for(Menu menu : menus){
            List<MenuItem> menuItems = menu.getMenuItems();
            for(MenuItem menuItem : menuItems){
                List<String> strings = Arrays.asList(menuItem.getIngredients());
                if(strings.contains("chicken")){
                    specialOffer.add(menu);
                }

            }
        }
        rest.setSpecialOffers(specialOffer);
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

    public static Result makeReservation(){
        OAuth2Token token = (OAuth2Token) Cache.get("token");
        Restaurant rest = (Restaurant) Cache.get("searchResult");


        return ok(reservation.render("Make Reservation", null, rest));
    }

    public static Result reserve() throws Exception {
        OAuth2Token token = (OAuth2Token) Cache.get("token");
        Restaurant rest = (Restaurant) Cache.get("searchResult");

        List<String> menuItems = new LinkedList<String>();
        DynamicForm dynamicForm = form().bindFromRequest();
        for(Menu menu : rest.getMenus()){
            for(MenuItem menuItem : menu.getMenuItems()){
                String menuItemId = dynamicForm.get(menuItem.getName());
                if(menuItemId == null){
                    continue;
                }
                menuItems.add(menuItemId);
            }
        }


        HttpConnector httpConnector = new HttpConnector();
        httpConnector.doPostJson(menuItems, "http://localhost:8080/dit-api/public/admin/reserve/");

        Logger.info("itemSelector is: " + menuItems.size());

        return ok(sucess.render("Success", null, rest));
    }
}
