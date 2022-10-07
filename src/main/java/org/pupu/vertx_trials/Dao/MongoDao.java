package org.pupu.vertx_trials.dao;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.model.DatabaseConfig;
import org.pupu.vertx_trials.model.Employee;

import java.util.List;

public interface MongoDao {
  Future<String> insertRecordJson(DatabaseConfig db, Employee employee, Vertx vertx);
  Future<Void> insertCollection(DatabaseConfig db, Vertx vertx);
  Future<JsonObject> updateRecordJson(DatabaseConfig db, Employee employee, Vertx vertx);
  Future<JsonObject> deleteRecordJson(DatabaseConfig db, Employee employee, Vertx vertx);
  Future<Void> deleteCollection(DatabaseConfig db, Vertx vertx);
  Future<JsonObject> showRecordJson(DatabaseConfig db, Employee employee, Vertx vertx);
  Future<List<JsonObject>> showCollectionRecords(DatabaseConfig db, Vertx vertx);
  Future<List<String>> showCollections(DatabaseConfig db, Vertx vertx);
}
