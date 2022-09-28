package org.pupu.vertx_trials.Dao;

import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.model.Database;

public interface MongoDao {

  void insertRecord(Database db, JsonObject employeeJson);

  void showRecord(Database db, JsonObject employeeJson);

  void deleteRecord(Database db, JsonObject employeeJson);

  void updateRecord(Database db, JsonObject employeeJson, String NewID);

  void showCollection(Database db);

  void showDatabaseCollections(Database db);
}
