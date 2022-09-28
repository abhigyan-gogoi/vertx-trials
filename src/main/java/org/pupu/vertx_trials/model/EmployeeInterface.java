package org.pupu.vertx_trials.model;

import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.Dao.MongoDao;

public interface EmployeeInterface {
  JsonObject getEmployeeJson();
  MongoDao getMongoDao();
}
