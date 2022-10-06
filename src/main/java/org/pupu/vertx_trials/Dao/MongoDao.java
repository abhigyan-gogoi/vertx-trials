package org.pupu.vertx_trials.dao;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.NewEmployee;

public interface MongoDao {
  Future<String> insertRecordJson(Database db, NewEmployee employee, Vertx vertx);
  Future<JsonObject> updateRecordJson(Database db, NewEmployee employee, String update, Vertx vertx);
  Future<JsonObject> showRecordJson(Database db, NewEmployee employee, Vertx vertx);
  Future<JsonObject> deleteRecordJson(Database db, NewEmployee employee, Vertx vertx);
  void showCollection(Database db);
  void showDatabaseCollections(Database db);
}
