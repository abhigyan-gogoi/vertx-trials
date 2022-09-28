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
  private final String _id;
  private final String new_id;

  public MongoPut
    (String uri, String db, String cl, String _id, String new_id) {
    this.uri = uri;
    this.db = db;
    this.cl = cl;
    this._id = _id;
    this.new_id = new_id;
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
      .put("_id", this._id);
    // Create JSON Object to update Last Name
    JsonObject update = new JsonObject()
      .put("$set", new JsonObject()
        .put("_id", this.new_id));
    // Send POST request to Mongo DB server
    // Use insert method in MongoClient
    client.updateCollection(this.cl, query, update, res -> {
      if (res.succeeded()){
        System.out.println("Employee "+this._id+" Updated to " + this.new_id);
      } else {
        // Failure if record exists
        System.out.println("Employee "+this._id+" does not exist in " + this.cl + " Collection");
      }
    });
  }
}
