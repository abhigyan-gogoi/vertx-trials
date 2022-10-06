package org.pupu.vertx_trials.service;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.NewEmployee;

public interface EmployeeService {
  Future<JsonObject> showEmployee(Database db, NewEmployee employee, RoutingContext routingContext);
  String getResponseString();
  JsonObject getResponse();
}
