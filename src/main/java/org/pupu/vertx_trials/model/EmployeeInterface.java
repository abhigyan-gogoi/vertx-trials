package org.pupu.vertx_trials.model;

import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.Dao.MongoDao;

public interface EmployeeInterface {
  String get_id();
  String getFirst_name();
  String getLast_name();
  void set_id(String _id);
  void setFirst_name(String first_name);
  void setLast_name(String last_name);
  JsonObject getEmployeeJson();
  MongoDao getMongoDao();
}
