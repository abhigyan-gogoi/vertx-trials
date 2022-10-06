package org.pupu.vertx_trials.dao;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.NewEmployee;

public interface MongoDao {

  void insertRecord(Database db, JsonObject employeeJson);

  void deleteRecord(Database db, JsonObject employeeJson);

  void updateRecord(Database db, JsonObject employeeJson, String NewLastName);

  void showRecord(Database db, JsonObject employeeJson);

  Future<JsonObject> showRecordJson(Database db, NewEmployee employee);

  void showCollection(Database db);

  void showDatabaseCollections(Database db);
}
