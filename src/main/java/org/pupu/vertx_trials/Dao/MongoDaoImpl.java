package org.pupu.vertx_trials.Dao;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.EmployeeImpl;

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
  public void showRecord(Database db, EmployeeImpl employeeImpl) {

  }

  @Override
  public void deleteRecord(Database db, EmployeeImpl employeeImpl) {

  }

  @Override
  public void updateRecord(Database db, EmployeeImpl employeeImpl) {

  }

  @Override
  public void showCollection(Database db, EmployeeImpl employeeImpl) {

  }

  @Override
  public void showDatabaseCollections(Database db, EmployeeImpl employeeImpl) {

  }

}
