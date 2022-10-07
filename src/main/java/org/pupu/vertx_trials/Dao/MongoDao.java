package org.pupu.vertx_trials.dao;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.Employee;

import java.util.List;

public interface MongoDao {
  Future<String> insertRecordJson(Database db, Employee employee, Vertx vertx);
  Future<Void> insertCollection(Database db, Vertx vertx);
  Future<JsonObject> updateRecordJson(Database db, Employee employee, Vertx vertx);
  Future<JsonObject> deleteRecordJson(Database db, Employee employee, Vertx vertx);
  Future<Void> deleteCollection(Database db, Vertx vertx);
  Future<JsonObject> showRecordJson(Database db, Employee employee, Vertx vertx);
  Future<List<JsonObject>> showCollectionRecords(Database db, Vertx vertx);
  Future<List<String>> showCollections(Database db, Vertx vertx);
}
