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
  @Override
  public Future<String> insertRecordJson(Database db, Employee employee, Vertx vertx) {
    return createMongoClient(db, vertx)
      .insert(db.getCollectionName(), employee.getEmployeeJson());
  }

  @Override
  public Future<Void> insertCollection(Database db, Vertx vertx) {
    return createMongoClient(db, vertx)
      .createCollection(db.getCollectionName());
  }

  @Override
  public Future<JsonObject> updateRecordJson(Database db, Employee employee, Vertx vertx) {
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("_id", employee.get_id());
    log.debug("Query: {}", query.encodePrettily());
    // Send PUT request to Mongo DB server
    // Use findOneAndDelete method in MongoClient
    return createMongoClient(db, vertx)
      .findOneAndReplace(db.getCollectionName(), query, employee.getEmployeeJson());
  }

  @Override
  public Future<JsonObject> showRecordJson(Database db, Employee employee, Vertx vertx) {
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("_id", employee.get_id());
    // Create JSON Object for fields
    JsonObject fields = new JsonObject();
    return createMongoClient(db, vertx).findOne(db.getCollectionName(), query, fields);
  }

  @Override
  public Future<List<JsonObject>> showCollectionRecords(Database db, Vertx vertx) {
    // Create JSON Object for query
    JsonObject query = new JsonObject();
    return createMongoClient(db, vertx).find(db.getCollectionName(), query);
  }

  @Override
  public Future<List<String>> showCollections(Database db, Vertx vertx) {
    return createMongoClient(db, vertx).getCollections();
  }

  @Override
  public Future<JsonObject> deleteRecordJson(Database db, Employee employee, Vertx vertx) {
    // Create JSON Object for query
    JsonObject query = new JsonObject()
      .put("_id", employee.get_id());
    // Send POST request to Mongo DB server
    // Use findOneAndDelete method in MongoClient
    return createMongoClient(db, vertx).findOneAndDelete(db.getCollectionName(), query);
  }

  @Override
  public Future<Void> deleteCollection(Database db, Vertx vertx) {
    return createMongoClient(db, vertx).dropCollection(db.getCollectionName());
  }

  private MongoClient createMongoClient(Database db, Vertx vertx) {
    // Set MongoDB config
    db.setMongoConfig();
    // Create and return MongoClient
    return MongoClient.createShared(vertx, db.getDbConfig());
  }
}
