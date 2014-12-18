package models;

import com.dit.account.User;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
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
            User user = new Gson().fromJson(dbObject.toString(), User.class);
            ObjectId objectId = (ObjectId) dbObject.get("_id");
            user.setId(objectId.toString());
            BasicDBObject basicDBObject = (BasicDBObject) dbObject.get("person");
            BasicDBObject restaurant = (BasicDBObject) basicDBObject.get("restaurant");
            ObjectId resId = (ObjectId) restaurant.get("_id");
            user.getPerson().getRestaurant().setId(resId.toString());
            return user;
        } catch (UnknownHostException e) {
            Logger.error("Error occur while getting user", e);
        }
        return null;
    }



}
