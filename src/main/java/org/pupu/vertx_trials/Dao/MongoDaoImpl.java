package org.pupu.vertx_trials.Dao;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.pupu.vertx_trials.model.Database;

public class MongoDaoImpl implements MongoDao{
  @Override
  public void insertRecord(Database db, JsonObject employeeJson) {
    // Create JSON object for connecting to MongoDB server
    JsonObject dbConfig = new JsonObject()
      .put("connection_uri", db.getDbUri())
      .put("db_name", db.getDatabaseName())
      ;
    // Create vertx mongo client to insert new employee record
    MongoClient client = MongoClient.createShared(Vertx.vertx(), dbConfig);
    // Execute the insert query in MongoDb
    client.insert(db.getCollectionName(), employeeJson, res ->{
      if (res.succeeded()){
        System.out.println("Employee "+ employeeJson.getString("_id")+" stored in employees Collection");
      } else {
        // Failure if record exists
        System.out.println("Employee "+ employeeJson.getString("_id")+" already exists in employees Collection");
      }
    });
  }

  @Override
  public void showRecord(Database db, JsonObject employeeJson) {
    // TODO
    // Change to find specific record EMPTY query
    // Create config for mongo database connection
    JsonObject mongoConfig = new JsonObject()
      .put("connection_uri", db.getDbUri())
      .put("db_name", db.getDatabaseName())
      ;
    // Create MongoClient
    MongoClient client = MongoClient.createShared(Vertx.vertx(), mongoConfig);
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
  public void deleteRecord(Database db, JsonObject employeeJson) {
    // Create config for mongo database connection
    JsonObject mongoConfig = new JsonObject()
      .put("connection_uri", db.getDbUri())
      .put("db_name", db.getDatabaseName())
      ;
    // Create MongoClient
    MongoClient client = MongoClient.createShared(Vertx.vertx(), mongoConfig);
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("_id", employeeJson.getString("_id"));
    // Send POST request to Mongo DB server
    // Use insert method in MongoClient
    client.removeDocuments(db.getCollectionName(), query, res -> {
      if (res.succeeded()){
        System.out.println("Employee "+ employeeJson.getString("_id")+" Deleted from " + db.getCollectionName()+" Collection");
      } else {
        // Failure if record exists
        System.out.println("Employee "+ employeeJson.getString("_id")+" already exists in  "+ db.getCollectionName()+" Collection");
      }
    });
  }

  @Override
  public void updateRecord(Database db, JsonObject employeeJson, String NewID) {
    // Create config for mongo database connection
    JsonObject mongoConfig = new JsonObject()
      .put("connection_uri", db.getDbUri())
      .put("db_name", db.getDatabaseName())
      ;
    // Create MongoClient
    MongoClient client = MongoClient.createShared(Vertx.vertx(), mongoConfig);
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("_id", employeeJson.getString("_id"))
      ;
    // Create JSON Object to update Last Name
    JsonObject update = new JsonObject()
      .put("$set", new JsonObject().put("_id", NewID))
      ;
    // Send POST request to Mongo DB server
    // Use updateCollection method in MongoClient
    client.updateCollection(db.getCollectionName(), query, update, res -> {
      if (res.succeeded()){
        System.out.println("Employee "+employeeJson.getString("_id")+" Updated to " + NewID);
      } else {
        System.out.println("Employee "+employeeJson.getString("_id")+" does not exist in " + db.getCollectionName() + " Collection");
      }
    });
  }

  @Override
  public void showCollection(Database db) {
    // Create config for mongo database connection
    JsonObject mongoConfig = new JsonObject()
      .put("connection_uri", db.getDbUri())
      .put("db_name", db.getDatabaseName())
      ;
    // Create MongoClient
    MongoClient client = MongoClient.createShared(Vertx.vertx(), mongoConfig);
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
    // Create config for mongo database connection
    JsonObject mongoConfig = new JsonObject()
      .put("connection_uri", db.getDbUri())
      .put("db_name", db.getDatabaseName())
      ;
    // Create MongoClient
    MongoClient client = MongoClient.createShared(Vertx.vertx(), mongoConfig);
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
