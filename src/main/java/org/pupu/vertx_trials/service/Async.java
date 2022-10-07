package org.pupu.vertx_trials.service;

import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.Employee;

public interface Async {
  void showEmployee(RoutingContext ctx, Vertx vertx, EmployeeService service, Database db, Employee employee, String restCall);
  void updateEmployee(RoutingContext ctx, Vertx vertx, EmployeeService service, Database db, Employee employee, String restCall);
  void deleteEmployee(RoutingContext ctx, Vertx vertx, EmployeeService service, Database db, Employee employee, String restCall);
  void insertEmployee (RoutingContext ctx, Vertx vertx, EmployeeService service, Database db, Employee employee, String restCall);
  void showCollection(RoutingContext ctx, Vertx vertx, DatabaseService service, Database db, String restCall);
  void deleteCollection(RoutingContext ctx, Vertx vertx, DatabaseService service, Database db, String restCall);
  void insertCollection(RoutingContext ctx, Vertx vertx, DatabaseService service, Database db, String restCall);
  void showDatabase(RoutingContext ctx, Vertx vertx, DatabaseService service, Database db, String restCall);
}
