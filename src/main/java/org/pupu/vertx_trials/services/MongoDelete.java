package org.pupu.vertx_trials.services;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoDelete extends AbstractVerticle {
  // URI for local Mongo DataBase
  private final String uri;
  // Mongo DataBase name
  private final String db;
  // Mongo Database Collection Name
  private final String cl;
  private final String employee_id;

  public MongoDelete
    (String uri, String db, String cl, String employee_id) {
    this.uri = uri;
    this.db = db;
    this.cl = cl;
    this.employee_id = employee_id;
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
      .put("Employee_ID", this.employee_id);
    // Send POST request to Mongo DB server
    // Use insert method in MongoClient
    client.removeDocuments(this.cl, query, res -> {
      if (res.succeeded()){
        System.out.println("Employee record Deleted from " + this.cl+" Collection");
      } else {
        // Failure if record exists
        System.out.println("Employee record already exists in  "+ this.cl+" Collection");
      }
    });
  }
}
