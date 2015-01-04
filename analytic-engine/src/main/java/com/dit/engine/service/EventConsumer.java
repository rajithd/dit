package com.dit.engine.service;

import com.mongodb.*;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;


@Component("eventC1")
public class EventConsumer implements Serializable {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    public void receiveMessage(String message) throws UnknownHostException {
        logger.info(" ================= Message received ================");
        logger.info(message);

//        User user = new Gson().fromJson(message, User.class);

//        logger.info("Analysing data for user " + user.getId());

        MongoClient mongoClient = new MongoClient();
        DB db = mongoClient.getDB("dit");
        DBCollection collection = db.getCollection("reservation");

        DBCollection menus = db.getCollection("menus");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("userId", "54a183145194baced32b6b3d");
        DBCursor cursor = collection.find(searchQuery);

        Set<String> integredients = new HashSet<String>();

        Set<String> menuIds = new HashSet<String>();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            String menuId = (String) dbObject.get("menuId");
            menuIds.add(menuId);
        }

        for (String menuId : menuIds) {
            BasicDBObject menuSearchQ = new BasicDBObject();
            menuSearchQ.put("_id", new ObjectId(menuId));

            DBObject menuObject = menus.findOne(menuSearchQ);

            BasicDBList menuList = (BasicDBList) menuObject.get("menuItems");
            for(Object dbObject : menuList){
                DBObject menuObj = (DBObject) dbObject;
                BasicDBList ingredientsDBList = (BasicDBList) menuObj.get("ingredients");

                for(Object obj : ingredientsDBList){
                    integredients.add((String) obj);
                }
            }
        }

        logger.info("=============>>>" + integredients);


//
//        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("Zips");
//        JavaSparkContext sc = new JavaSparkContext(sparkConf);
//        Configuration bsonConfig = new Configuration();
//        bsonConfig.set("mongo.job.input.format", "com.mongodb.hadoop.MongoInputFormat.class");
//        bsonConfig.set("mongo.input.uri", "mongodb://127.0.0.1:27017/dit.reservation");
//        bsonConfig.set("mongo.output.uri", "mongodb://127.0.0.1:27017/dit.output");
//        JavaPairRDD<Object,BSONObject> zipData = sc.newAPIHadoopRDD(bsonConfig, MongoInputFormat.class, Object.class, BSONObject.class);
//        JavaPairRDD<String,Integer> statePopsMap = zipData.mapToPair(new PairFunction<Tuple2<Object,BSONObject>,String,Integer>(){
//            public Tuple2<String,Integer> call(Tuple2<Object,BSONObject> zip){
//                Tuple2<String, Integer> tuple = new Tuple2<String,Integer>((String)zip._2.get("state"),(Integer)zip._2.get("pop"));
//                return tuple;
//            }
//        });
//        JavaPairRDD<String,Integer> statesPops = statePopsMap.reduceByKey(new Function2<Integer,Integer, Integer>(){
//            public Integer call(Integer i1, Integer i2){
//                return i1 + i2;
//            }
//        });
//
//        statesPops.saveAsNewAPIHadoopFile("file:///bogus", Object.class, Object.class, MongoOutputFormat.class, bsonConfig);
////		statesPops.saveAsTextFile(HDFS_HOST + "/state_pops.txt");
//        sc.close();


    }

}
