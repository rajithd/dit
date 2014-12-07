package controllers;

import com.dit.account.User;
import models.UserDao;
import play.Logger;
import play.cache.Cache;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import util.HttpConnector;
import views.html.login;

import static play.data.Form.form;

public class LoginController extends Controller {

    public static Result login() {
        return ok(login.render());
    }

    public static Result submit() throws Exception {
        DynamicForm dynamicForm = form().bindFromRequest();
        Logger.info("Username is: " + dynamicForm.get("username"));
        UserDao userDao = new UserDao();
        String username = dynamicForm.get("username");
        String password = dynamicForm.get("password");
        String restaurantRegNo = dynamicForm.get("restaurantRegNo");

        HttpConnector httpConnector = new HttpConnector();
        User user = httpConnector.doGet("http://localhost:8080/dit-api/public/admin/user/" + username + "/" + password + "/" + restaurantRegNo, User.class);

        if (user == null) {
            return ok(login.render());
        }
        Cache.set("user", user, 3600 * 60 * 60 * 60);
        return redirect(routes.Application.index());
    }

    public static Result logout() {
        Cache.remove("user");
        return redirect(routes.LoginController.login());
    }

}
