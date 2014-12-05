package models;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class MongoConnection {

    private static DB db;

    private MongoConnection() {
    }

    public static DB getDBConnection() throws UnknownHostException {
        if (db == null) {
            MongoClient mongoClient = new MongoClient();
            db = mongoClient.getDB("dit");
        }
        return db;


    }

}
