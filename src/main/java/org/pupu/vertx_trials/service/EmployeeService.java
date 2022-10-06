package org.pupu.vertx_trials.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.NewEmployee;

public interface EmployeeService {
  Future<JsonObject> showEmployee(Database db, NewEmployee employee, Vertx vertx);
  Future<JsonObject> deleteEmployee(Database db, NewEmployee employee, Vertx vertx);
  Future<JsonObject> updateEmployee(Database db, NewEmployee employee, String update, Vertx vertx);
  Future<String> insertEmployee(Database db, NewEmployee employee, Vertx vertx);
}
