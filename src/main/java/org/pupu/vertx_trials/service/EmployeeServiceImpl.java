package org.pupu.vertx_trials.service;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.pupu.vertx_trials.dao.MongoDao;
import org.pupu.vertx_trials.dao.MongoDaoImpl;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.EmployeeInterface;
import org.pupu.vertx_trials.model.NewEmployee;
import org.pupu.vertx_trials.model.NewEmployeeImpl;

public class EmployeeServiceImpl implements EmployeeService{
  private NewEmployee employee;
  private MongoDao mongoDao;
  private JsonObject response;

  private String responseString;

  public EmployeeServiceImpl() {
    this.employee = new NewEmployeeImpl();
    this.mongoDao = new MongoDaoImpl();
  }

  public JsonObject getResponse() {
    return response;
  }

  public void setResponse(JsonObject response) {
    this.response = response;
  }

  @Override
  public String getResponseString() {
    return responseString;
  }

  public void setResponseString(String responseString) {
    this.responseString = responseString;
  }

  @Override
  public Future<JsonObject> showEmployee(Database db, NewEmployee employee, RoutingContext routingContext) {
//    getFutureJson(this.mongoDao.showRecordJson(db, employee), routingContext);
    return this.mongoDao.showRecordJson(db, employee);
  }

  public void getFutureJson(Future<JsonObject> fJson, RoutingContext routingContext) {
    fJson
      .onSuccess(res -> {
        System.out.println("GET SUCCESS");
        routingContext.put("RESPONSE", "TODO");
      })
    ;
  }
}
