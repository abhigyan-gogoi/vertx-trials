package org.pupu.vertx_trials.Dao;

import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.EmployeeImpl;

public interface MongoDao {

  void insertRecord(Database db, JsonObject employeeJson);

  void showRecord(Database db, EmployeeImpl employeeImpl);

  void deleteRecord(Database db, EmployeeImpl employeeImpl);

  void updateRecord(Database db, EmployeeImpl employeeImpl);

  void showCollection(Database db, EmployeeImpl employeeImpl);

  void showDatabaseCollections(Database db, EmployeeImpl employeeImpl);
}
