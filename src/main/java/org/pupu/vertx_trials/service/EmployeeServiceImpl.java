package org.pupu.vertx_trials.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.dao.MongoDao;
import org.pupu.vertx_trials.dao.MongoDaoImpl;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.NewEmployee;

public class EmployeeServiceImpl implements EmployeeService{
  private final MongoDao mongoDao;

  public EmployeeServiceImpl() {
    this.mongoDao = new MongoDaoImpl();
  }

  @Override
  public Future<JsonObject> showEmployee(Database db, NewEmployee employee, Vertx vertx) {
    return this.mongoDao.showRecordJson(db, employee, vertx);
  }

  @Override
  public Future<JsonObject> deleteEmployee(Database db, NewEmployee employee, Vertx vertx) {
    return this.mongoDao.deleteRecordJson(db, employee, vertx);
  }
}
