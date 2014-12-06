package models;

import com.dit.account.User;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import play.Logger;

import java.net.UnknownHostException;

public class UserDao {

    public User findUser(String username, String password, String regNo) {
        DB db = null;
        try {
            db = MongoConnection.getDBConnection();
            DBCollection table = db.getCollection("users");

            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("username", username);
            searchQuery.put("password", password);
            searchQuery.put("person.restaurant.regNo", regNo);

            DBObject dbObject = table.findOne(searchQuery);
            if (dbObject == null) {
                return null;
            }
            return new Gson().fromJson(dbObject.toString(), User.class);
        } catch (UnknownHostException e) {
            Logger.error("Error occur while getting user", e);
        }
        return null;
    }

}
