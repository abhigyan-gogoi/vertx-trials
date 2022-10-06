package org.pupu.vertx_trials.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.model.DatabaseConfig;
import org.pupu.vertx_trials.model.Employee;

public interface EmployeeService {
  Future<JsonObject> showEmployee(DatabaseConfig db, Employee employee, Vertx vertx);
  Future<JsonObject> deleteEmployee(DatabaseConfig db, Employee employee, Vertx vertx);
  Future<JsonObject> updateEmployee(DatabaseConfig db, Employee employee, String update, Vertx vertx);
  Future<String> insertEmployee(DatabaseConfig db, Employee employee, Vertx vertx);
}
