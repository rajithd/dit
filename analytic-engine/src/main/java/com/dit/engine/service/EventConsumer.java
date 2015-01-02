package com.dit.engine.service;

import com.mongodb.*;
import com.mongodb.hadoop.MongoInputFormat;
import com.mongodb.hadoop.MongoOutputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.bson.BSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.io.Serializable;
import java.net.UnknownHostException;


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

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("userId", "5493fac53004f7de6796936d");
        DBObject user = collection.findOne(searchQuery);
        String menuId = (String) user.get("menuId");

//
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("Zips");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        Configuration bsonConfig = new Configuration();
        bsonConfig.set("mongo.job.input.format", "com.mongodb.hadoop.MongoInputFormat.class");
        bsonConfig.set("mongo.input.uri", "mongodb://127.0.0.1:27017/dit.reservation");
        bsonConfig.set("mongo.output.uri", "mongodb://127.0.0.1:27017/dit.specialOffers");
        JavaPairRDD<Object,BSONObject> zipData = sc.newAPIHadoopRDD(bsonConfig, MongoInputFormat.class, Object.class, BSONObject.class);
        JavaPairRDD<String,Integer> statePopsMap = zipData.mapToPair(new PairFunction<Tuple2<Object,BSONObject>,String,Integer>(){
            public Tuple2<String,Integer> call(Tuple2<Object,BSONObject> zip){
                Tuple2<String, Integer> tuple = new Tuple2<String,Integer>((String)zip._2.get("state"),(Integer)zip._2.get("pop"));
                return tuple;
            }
        });
        JavaPairRDD<String,Integer> statesPops = statePopsMap.reduceByKey(new Function2<Integer,Integer, Integer>(){
            public Integer call(Integer i1, Integer i2){
                return i1 + i2;
            }
        });

        statesPops.saveAsNewAPIHadoopFile("file:///bogus", Object.class, Object.class, MongoOutputFormat.class, bsonConfig);
//		statesPops.saveAsTextFile(HDFS_HOST + "/state_pops.txt");
        sc.close();
    }

}
