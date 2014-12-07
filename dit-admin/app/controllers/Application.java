package controllers;

import com.dit.Zone;
import com.dit.account.User;
import org.bson.types.ObjectId;
import play.cache.Cache;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import util.HttpConnector;
import views.html.index;
import views.html.table;
import views.html.zones;

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

}
