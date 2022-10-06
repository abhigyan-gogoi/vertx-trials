package org.pupu.vertx_trials.dao;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.NewEmployee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDaoImpl implements MongoDao {
  private static final Logger log = LoggerFactory.getLogger(MongoDaoImpl.class);
  private JsonObject dbConfig;
  public void setMongoConfig(Database db) {
    // Create JSON object for connecting to MongoDB server
    this.dbConfig = new JsonObject()
      .put("connection_uri", db.getDbUri())
      .put("db_name", db.getDatabaseName())
      ;
  }

  @Override
  public Future<String> insertRecordJson(Database db, NewEmployee employee, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    return client.insert(db.getCollectionName(), employee.getEmployeeJson());
  }

  @Override
  public Future<JsonObject> updateRecordJson(Database db, NewEmployee employee, String update, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("last_name", employee.getLast_name());
    // Create JSON Object for update
    employee.setLast_name(update);
    // Send PUT request to Mongo DB server
    // Use findOneAndDelete method in MongoClient
    return client.findOneAndUpdate(db.getCollectionName(), query, employee.getEmployeeJson());
  }

  @Override
  public Future<JsonObject> showRecordJson(Database db, NewEmployee employee, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("_id", employee.get_id());
    // Create JSON Object for fields
    JsonObject fields = new JsonObject();
    return client.findOne(db.getCollectionName(), query, fields);
  }

  @Override
  public Future<JsonObject> deleteRecordJson(Database db, NewEmployee employee, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("_id", employee.get_id());
    // Send POST request to Mongo DB server
    // Use findOneAndDelete method in MongoClient
    return client.findOneAndDelete(db.getCollectionName(), query);
  }

  @Override
  public void showCollection(Database db) {
    // Create MongoClient
    MongoClient client = MongoClient.createShared(Vertx.vertx(), this.dbConfig);
    // Create an empty JSON Object
    // For finding all records in collection
    // Specify Field for specific record
    JsonObject emptyJson = new JsonObject();
    // Send GET request to Mongo DB server
    // Use find method in MongoClient
    client.find(db.getCollectionName(), emptyJson, res -> {
      if (res.succeeded()){
        for (JsonObject json : res.result()) {
          System.out.println(json.encodePrettily());
        }
        System.out.println("All Employee records displayed");
      } else {
        // Failure
        System.out.println("Failed to read Collection");
      }
    });
  }

  @Override
  public void showDatabaseCollections(Database db) {
    // Create MongoClient
    MongoClient client = MongoClient.createShared(Vertx.vertx(), this.dbConfig);
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
        System.out.println("All "+db.getDatabaseName()+" Collections are displayed");
      } else {
        // Failure
        System.out.println("Failed to get all "+db.getDatabaseName()+" Collections");
      }
    });
  }

}
