package controllers;

import com.dit.Menu;
import com.dit.MenuItem;
import com.dit.Person;
import com.dit.Zone;
import com.dit.account.Permission;
import com.dit.account.Role;
import com.dit.account.User;
import models.PersonFactory;
import org.bson.types.ObjectId;
import play.Logger;
import play.cache.Cache;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import util.HttpConnector;
import views.html.*;

import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

public class Application extends Controller {

    public static Result index() {
        User user = (User) Cache.get("user");
        if (user == null) {
            return redirect(routes.LoginController.login());
        }

        return ok(index.render("Home", user, user.getPerson().getRestaurant()));
    }

    public static Result zones() {
        User user = (User) Cache.get("user");
        if (user == null) {
            return redirect(routes.LoginController.login());
        }
        return ok(zones.render("Zones", user, user.getPerson().getRestaurant()));
    }

    public static Result zonesSubmit() throws Exception {
        User user = (User) Cache.get("user");
        DynamicForm dynamicForm = form().bindFromRequest();
        String zoneName = dynamicForm.get("zoneName");
        String numberOfTables = dynamicForm.get("numberOfTables");
        String resId = user.getPerson().getRestaurant().getId();
        Zone zone = new Zone();
        zone.setId(new ObjectId().toString());
        zone.setName(zoneName);
        zone.setNumberOfTables(Integer.parseInt(numberOfTables));

        user.getPerson().getRestaurant().getZones().add(zone);

        HttpConnector httpConnector = new HttpConnector();
        httpConnector.doPostJson(zone, "http://localhost:8080/dit-api/public/admin/" + resId + "/zone");

        return redirect(routes.Application.zones());
    }

    public static Result getTableStructure(String zoneId) throws Exception {
        User user = (User) Cache.get("user");
        if (user == null) {
            return redirect(routes.LoginController.login());
        }

        HttpConnector httpConnector = new HttpConnector();
        Zone zone = httpConnector.doGet("http://localhost:8080/dit-api/public/admin/" + user.getPerson().getRestaurant().getId() + "/zone/" + zoneId, Zone.class);

        return ok(table.render("Table Structure", user, user.getPerson().getRestaurant(), zone));

    }

    public static Result users() {
        User user = (User) Cache.get("user");
        if (user == null) {
            return redirect(routes.LoginController.login());
        }
        return ok(users.render("Create Users", user, user.getPerson().getRestaurant()));
    }

    public static Result userSubmit() throws Exception {
        User user = (User) Cache.get("user");
        DynamicForm dynamicForm = form().bindFromRequest();
        String username = dynamicForm.get("username");
        String password = dynamicForm.get("password");
        String role = dynamicForm.get("role");
        String firstName = dynamicForm.get("firstName");
        String lastName = dynamicForm.get("lastName");
        String email = dynamicForm.get("email");
        Logger.info("Role ==>" + role);

        PersonFactory personFactory = new PersonFactory();
        Person person = personFactory.personFactory(firstName, lastName, role);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setRoles(createRoles(role));
        newUser.setPerson(person);


        HttpConnector httpConnector = new HttpConnector();
        httpConnector.doPostJson(newUser, "http://localhost:8080/dit-api/public/admin/" + user.getId() + "/create/user/" + role);
        return redirect(routes.Application.index());
    }

    private static List<Role> createRoles(String permissionString) throws IllegalAccessException {
        List<Role> roles = new ArrayList<Role>();
        Role role = new Role();
        role.setName(permissionString);
        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(Permission.getPermissionVal(permissionString));
        role.setPermissions(permissions);
        roles.add(role);
        return roles;
    }

    public static Result reports() {
        User user = (User) Cache.get("user");
        if (user == null) {
            return redirect(routes.LoginController.login());
        }
        return ok(reports.render("Reports", user, user.getPerson().getRestaurant()));
    }

    public static Result menus() {
        User user = (User) Cache.get("user");
        if (user == null) {
            return redirect(routes.LoginController.login());
        }
        return ok(menus.render("Menus", user, user.getPerson().getRestaurant(), new Menu()));
    }

    public static Result menuSubmit() throws Exception {
        User user = (User) Cache.get("user");
        DynamicForm dynamicForm = form().bindFromRequest();
        String menuName = dynamicForm.get("menuName");

        Menu menu = new Menu();
        menu.setName(menuName);

        HttpConnector httpConnector = new HttpConnector();
        httpConnector.doPostJson(menu, "http://localhost:8080/dit-api/public/admin/" + user.getPerson().getRestaurant().getId() + "/menus/");

        return ok(menus.render("Menus", user, user.getPerson().getRestaurant(), menu));
    }

    public static Result getMenuById(String menuId) throws Exception {
        User user = (User) Cache.get("user");
        if (user == null) {
            return redirect(routes.LoginController.login());
        }

        Cache.set("menuId", menuId, 3600*20*20);
        HttpConnector httpConnector = new HttpConnector();
        Menu menu = httpConnector.doGet("http://localhost:8080/dit-api/public/admin/menus/" + menuId, Menu.class);
        return ok(menus.render("Menus", user, user.getPerson().getRestaurant(), menu));
    }

    public static Result itemSubmit() throws Exception {
        User user = (User) Cache.get("user");
        String menuId = (String) Cache.get("menuId");
        DynamicForm dynamicForm = form().bindFromRequest();
        Logger.info("Saving menu : " + menuId);

        MenuItem menuItem = new MenuItem();
        menuItem.setName(dynamicForm.get("itemName"));
        menuItem.setDescription(dynamicForm.get("itemDescription"));
        menuItem.setPrice(Double.parseDouble(dynamicForm.get("itemPrice")));

        HttpConnector httpConnector = new HttpConnector();
        httpConnector.doPutJson(menuItem, "http://localhost:8080/dit-api/public/admin/menus/" + menuId);
        return ok(menus.render("Menus", user, user.getPerson().getRestaurant(), new Menu()));
    }

}
