package org.pupu.vertx_trials.dao;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.NewEmployee;

public class MongoDaoImpl implements MongoDao {
  private JsonObject dbConfig;

  public void createMongoConfig(Database db) {
    // Create JSON object for connecting to MongoDB server
    this.dbConfig = new JsonObject()
      .put("connection_uri", db.getDbUri())
      .put("db_name", db.getDatabaseName())
      ;
  }

  @Override
  public void insertRecord(Database db, JsonObject employeeJson) {
    // Create vertx mongo client to insert new employee record
    MongoClient client = MongoClient.createShared(Vertx.vertx(), this.dbConfig);
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
    // Create MongoClient config
    createMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(Vertx.vertx(), this.dbConfig);
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("_id", employeeJson.getString("_id"));
    // Create JSON Object for fields
    JsonObject fields = new JsonObject();
    // Create Future object
//    Future future = Future.future(res -> {
//      client.findOne(db.getCollectionName(), query, fields);
//    }).onSuccess(res -> {
//
//    })
//      ;
    // Send GET request to Mongo DB server
    // Use find method in MongoClient

//    client.find(db.getCollectionName(), query, res -> {
//      if (res.succeeded()){
//        for (JsonObject json : res.result()) {
//          System.out.println(json.encodePrettily());
//        }
//        System.out.println("Employee record displayed");
//      } else {
//        // Failure
//        System.out.println("Failed to read Record from DB");
//      }
//    });
    client.findOne(db.getCollectionName(), query, fields)
      .onSuccess(res -> {
        // Use service class
        System.out.println(res.encodePrettily());
      })
      .onFailure(err -> {
        System.out.println(err.getMessage());
      });
  }

  @Override
  public Future<JsonObject> showRecordJson(Database db, NewEmployee employee) {
    // Create MongoClient config
    createMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(Vertx.vertx(), this.dbConfig);
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("_id", employee.get_id());
    // Create JSON Object for fields
    JsonObject fields = new JsonObject();
    // Create Future object
//    Future future = Future.future(res -> {
//      client.findOne(db.getCollectionName(), query, fields);
//    }).onSuccess(res -> {
//
//    })
//      ;
    // Send GET request to Mongo DB server
    // Use find method in MongoClient

//    client.find(db.getCollectionName(), query, res -> {
//      final JsonArray response = new JsonArray();
//      if (res.succeeded()){
//        for (JsonObject json : res.result()) {
//          System.out.println(json.encodePrettily());
//          response.add(json);
//        }
//        System.out.println("Employee record displayed");
//      } else {
//        // Failure
//        System.out.println("Failed to read Record from DB");
//      }
//    });
    return client.findOne(db.getCollectionName(), query, fields);
//      .onSuccess(res -> {
//        // Use service class
//        System.out.println(res.encodePrettily());
//      })
//      .onFailure(err -> {
//        System.out.println(err.getMessage());
//      });
  }

  @Override
  public void deleteRecord(Database db, JsonObject employeeJson) {
    // Create MongoClient
    MongoClient client = MongoClient.createShared(Vertx.vertx(), this.dbConfig);
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
  public void updateRecord(Database db, JsonObject employeeJson, String NewLastName) {
    // Create MongoClient
    MongoClient client = MongoClient.createShared(Vertx.vertx(), this.dbConfig);
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("Last_name", employeeJson.getString("Last_name"))
      ;
    // Create JSON Object to update Last Name
    JsonObject update = new JsonObject().put("$set", new JsonObject().put("Last_name", NewLastName));
    // Send POST request to Mongo DB server
    // Use updateCollection method in MongoClient
    client.updateCollection(db.getCollectionName(), query, update, res -> {
      if (res.succeeded()){
        System.out.println("Employee "+employeeJson.getString("_id")+" Updated with new Last Name " + NewLastName);
      } else {
        System.out.println("Employee "+employeeJson.getString("_id")+" does not exist in " + db.getCollectionName() + " Collection");
      }
    });
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
