package org.pupu.vertx_trials.services;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.pupu.vertx_trials.model.EmployeeImpl;

public class MongoPost extends AbstractVerticle {
  // URI for local Mongo DataBase
  private final String uri;
  // Mongo DataBase name
  private final String db;
  // Mongo Database Collection Name
  private final String cl;

  public MongoPost(String uri, String db, String cl) {
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
    // Create an employee instance and return JSON Object
    EmployeeImpl employeeImpl = new EmployeeImpl();
    JsonObject employeeJson = employeeImpl.getEmployeeJson();
    // Send POST request to Mongo DB server
    // Use insert method in MongoClient
    client.insert(this.cl, employeeJson, res -> {
      if (res.succeeded()){
        System.out.println("Employee "+ employeeImpl.get_id()+" stored in employees Collection");
      } else {
        // Failure if record exists
        System.out.println("Employee "+ employeeImpl.get_id()+" already exists in employees Collection");
      }
    });
  }
}
