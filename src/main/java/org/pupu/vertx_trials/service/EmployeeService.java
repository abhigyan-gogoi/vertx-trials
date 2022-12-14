package org.pupu.vertx_trials.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.Employee;

public interface EmployeeService {
  Future<JsonObject> showEmployee(Database db, Employee employee, Vertx vertx);
  Future<JsonObject> deleteEmployee(Database db, Employee employee, Vertx vertx);
  Future<JsonObject> updateEmployee(Database db, Employee employee, Vertx vertx);
  Future<String> insertEmployee(Database db, Employee employee, Vertx vertx);
}
