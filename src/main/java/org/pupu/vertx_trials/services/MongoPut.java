package org.pupu.vertx_trials.services;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoPut extends AbstractVerticle {
  // URI for local Mongo DataBase
  private final String uri;
  // Mongo DataBase name
  private final String db;
  // Mongo Database Collection Name
  private final String cl;
  private final String last_name;

  public MongoPut
    (String uri, String db, String cl, String last_name) {
    this.uri = uri;
    this.db = db;
    this.cl = cl;
    this.last_name = last_name;
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
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("Last_name", this.last_name);
    // Create JSON Object to update Last Name
    JsonObject update = new JsonObject()
      .put("$set", new JsonObject()
        .put("Last_name", "Kumar"));
    // Send POST request to Mongo DB server
    // Use insert method in MongoClient
    client.updateCollection(this.cl, query, update, res -> {
      if (res.succeeded()){
        System.out.println("Employee record Updated from " + this.cl + " Collection");
      } else {
        // Failure if record exists
        System.out.println("Employee record does not exist in " + this.cl + " Collection");
      }
    });
  }
}
