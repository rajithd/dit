package controllers;

import com.dit.account.User;
import models.UserDao;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;
import views.html.login;

import static play.data.Form.form;

public class LoginController extends Controller {

    public static Result login() {
        return ok(login.render());
    }

    public static Result submit() {
        DynamicForm dynamicForm = form().bindFromRequest();
        Logger.info("Username is: " + dynamicForm.get("username"));
        UserDao userDao = new UserDao();
        User user = userDao.findUser(dynamicForm.get("username"), dynamicForm.get("password"));
        if (user == null) {
            return ok(login.render());
        }
        Http.Session session = Http.Context.current().session();
        session.put("username", user.getUsername());
        session.put("firstName", user.getPerson().getFirstName());
        return redirect(routes.Application.index());
    }

    public static Result logout(){
        Http.Session session = Http.Context.current().session();
        session.remove("username");
        session.remove("firstName");
        return redirect(routes.LoginController.login());
    }

}
