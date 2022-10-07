package org.pupu.vertx_trials.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.dao.MongoDao;
import org.pupu.vertx_trials.dao.MongoDaoImpl;
import org.pupu.vertx_trials.model.DatabaseConfig;
import org.pupu.vertx_trials.model.Employee;

public class EmployeeServiceImpl implements EmployeeService{
  private final MongoDao mongoDao;

  public EmployeeServiceImpl() {
    this.mongoDao = new MongoDaoImpl();
  }

  @Override
  public Future<JsonObject> showEmployee(DatabaseConfig db, Employee employee, Vertx vertx) {
    return this.mongoDao.showRecordJson(db, employee, vertx);
  }

  @Override
  public Future<JsonObject> deleteEmployee(DatabaseConfig db, Employee employee, Vertx vertx) {
    return this.mongoDao.deleteRecordJson(db, employee, vertx);
  }

  @Override
  public Future<JsonObject> updateEmployee(DatabaseConfig db, Employee employee, Vertx vertx) {
    return this.mongoDao.updateRecordJson(db, employee, vertx);
  }

  @Override
  public Future<String> insertEmployee(DatabaseConfig db, Employee employee, Vertx vertx) {
    return this.mongoDao.insertRecordJson(db, employee, vertx);
  }
}
