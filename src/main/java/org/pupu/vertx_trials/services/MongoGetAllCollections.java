package org.pupu.vertx_trials.services;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoGetAllCollections extends AbstractVerticle {
  // URI for local Mongo DataBase
  private final String uri;
  // Mongo DataBase name
  private final String db;

  public MongoGetAllCollections(String uri, String db) {
    this.uri = uri;
    this.db = db;
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
    // getCollections method gets all collections in DB
    // dropCollection("collection-name", AsyncHandler) to delete collection
    // CAUTION!! Deletes all records within the collection
    // createCollection("collection-name", AsyncHandler) to create collection
    client.getCollections(res -> {
      if (res.succeeded()){
        JsonObject json = new JsonObject()
          .put("List", "Collections");
        for (String collection : res.result()) {
          json.put("Collection-name", collection);
        }
        System.out.println(json.encodePrettily());
        System.out.println("All DB Collections are displayed");
      } else {
        // Failure
        System.out.println("Failed to get all DB Collections");
      }
    });
  }
}
