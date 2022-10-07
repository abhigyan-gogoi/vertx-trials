package org.pupu.vertx_trials.dao;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.pupu.vertx_trials.model.DatabaseConfig;
import org.pupu.vertx_trials.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MongoDaoImpl implements MongoDao {
  private static final Logger log = LoggerFactory.getLogger(MongoDaoImpl.class);
  private JsonObject dbConfig;
  public void setMongoConfig(DatabaseConfig db) {
    // Create JSON object for connecting to MongoDB server
    this.dbConfig = new JsonObject()
      .put("connection_uri", db.getDbUri())
      .put("db_name", db.getDatabaseName())
      ;
  }

  @Override
  public Future<String> insertRecordJson(DatabaseConfig db, Employee employee, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    log.debug("JSON: {}", employee.getEmployeeJson().encodePrettily());
    return client.insert(db.getCollectionName(), employee.getEmployeeJson());
  }

  @Override
  public Future<Void> insertCollection(DatabaseConfig db, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    return client.createCollection(db.getCollectionName());
  }

  @Override
  public Future<JsonObject> updateRecordJson(DatabaseConfig db, Employee employee, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("_id", employee.get_id());
    log.debug("Query: {}", query.encodePrettily());
//    JsonObject update = new JsonObject()
//      .put("first_name", employee.getFirst_name())
//      .put("last_name", employee.getLast_name());
    // Send PUT request to Mongo DB server
    // Use findOneAndDelete method in MongoClient
    return client.findOneAndReplace(db.getCollectionName(), query, employee.getEmployeeJson());
  }

  @Override
  public Future<JsonObject> showRecordJson(DatabaseConfig db, Employee employee, Vertx vertx) {
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
  public Future<List<JsonObject>> showCollectionRecords(DatabaseConfig db, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    // Create JSON Object for query
    JsonObject query = new JsonObject();
    return client.find(db.getCollectionName(), query);
  }

  @Override
  public Future<List<String>> showCollections(DatabaseConfig db, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    return client.getCollections();
  }

  @Override
  public Future<JsonObject> deleteRecordJson(DatabaseConfig db, Employee employee, Vertx vertx) {
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
  public Future<Void> deleteCollection(DatabaseConfig db, Vertx vertx) {
    // Set MongoDB config
    setMongoConfig(db);
    // Create MongoClient
    MongoClient client = MongoClient.createShared(vertx, this.dbConfig);
    return client.dropCollection(db.getCollectionName());
  }
}
