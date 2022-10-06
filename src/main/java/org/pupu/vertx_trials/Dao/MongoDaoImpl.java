package org.pupu.vertx_trials.dao;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
  public Future<String> insertRecordJson(Database db, Employee employee, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    log.debug("JSON: {}", employee.getEmployeeJson().encodePrettily());
    return client.insert(db.getCollectionName(), employee.getEmployeeJson());
  }

  @Override
  public Future<JsonObject> updateRecordJson(Database db, Employee employee, String update, Vertx vertx) {
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
  public Future<JsonObject> showRecordJson(Database db, Employee employee, Vertx vertx) {
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
  public Future<List<JsonObject>> showCollectionRecords(Database db, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    // Create JSON Object for query
    JsonObject query = new JsonObject();
    return client.find(db.getCollectionName(), query);
  }

  @Override
  public Future<List<String>> showCollections(Database db, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    return client.getCollections();
  }

  @Override
  public Future<JsonObject> deleteRecordJson(Database db, Employee employee, Vertx vertx) {
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
}
