package org.pupu.vertx_trials.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.Employee;

import java.util.List;

public interface EmployeeService {
  Future<JsonObject> showEmployee(Database db, Employee employee, Vertx vertx);
  Future<List<JsonObject>> showEmployeeRecords(Database db, Vertx vertx);
  Future<List<String>> showCollections(Database db, Vertx vertx);
  Future<JsonObject> deleteEmployee(Database db, Employee employee, Vertx vertx);
  Future<JsonObject> updateEmployee(Database db, Employee employee, String update, Vertx vertx);
  Future<String> insertEmployee(Database db, Employee employee, Vertx vertx);
}
