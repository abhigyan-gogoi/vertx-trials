package org.pupu.vertx_trials.services;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoGetCollection extends AbstractVerticle {
  // URI for local Mongo DataBase
  private final String uri;
  // Mongo DataBase name
  private final String db;
  // Mongo Database Collection Name
  private final String cl;

  public MongoGetCollection(String uri, String db, String cl) {
    this.uri = uri;
    this.db = db;
    this.cl = cl;
  }

  @Override
  public void start() {
    // Create config for mongo database connection
    JsonObject mongoConfig = new JsonObject()
      .put("connection_uri", this.uri)
      .put("db_name", this.db)
      ;
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, mongoConfig);
    // Create an empty JSON Object
    // For finding all records in collection
    JsonObject emptyJson = new JsonObject();
    // Send GET request to Mongo DB server
    // Use find method in MongoClient
    client.find(this.cl, emptyJson, res -> {
      if (res.succeeded()){
        for (JsonObject json : res.result()) {
          System.out.println(json.encodePrettily());
        }
        System.out.println("All Employee records displayed");
      } else {
        // Failure
        System.out.println("Failed to read DB Collection");
      }
    });
  }
}
