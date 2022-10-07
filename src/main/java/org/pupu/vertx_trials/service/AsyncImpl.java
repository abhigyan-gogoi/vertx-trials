package org.pupu.vertx_trials.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncImpl extends AbstractVerticle implements Async {
  private static final Logger log = LoggerFactory.getLogger(AsyncImpl.class);

  // Employee Service (Record/Document) Methods
  // GET -> Show
  @Override
  public void showEmployee(RoutingContext ctx, Vertx vertx, EmployeeService service, Database db, Employee employee, String restCall) {
    service.showEmployee(db, employee, vertx)
      .map(res -> new JsonArray().add(res))
      .onSuccess(res -> {
        res = emptyResultHandler(res, "ID does NOT exist in Database");
        responseHandler(ctx, restCall, res);
      });
  }

  // PUT -> Update
  @Override
  public void updateEmployee(RoutingContext ctx, Vertx vertx, EmployeeService service, Database db, Employee employee, String restCall) {
    service.updateEmployee(db, employee, vertx)
      .map(res -> new JsonArray().add(res))
      .onSuccess(res -> {
        res = emptyResultHandler(res, "ID does not exist in Database");
        res
          .add(new JsonObject()
            .put("Message", "Replaced above record with the one below"))
          .add(employee.getEmployeeJson());
        responseHandler(ctx, restCall, res);
      });
  }

  // DELETE -> Delete
  @Override
  public void deleteEmployee(RoutingContext ctx, Vertx vertx, EmployeeService service, Database db, Employee employee, String restCall) {
    service.deleteEmployee(db, employee, vertx)
      .map(res -> new JsonArray().add(res))
      .onSuccess(res -> {
        res = emptyResultHandler(res, "ID does not exist in Database");
        responseHandler(ctx, restCall, res);
      });
  }

  // POST -> Insert
  @Override
  public void insertEmployee (RoutingContext ctx, Vertx vertx, EmployeeService service, Database db, Employee employee, String restCall) {
    service.insertEmployee(db, employee, vertx)
      .map(res -> new JsonArray())
      .onSuccess(res -> {
        res = new JsonArray()
          .add(employee.getEmployeeJson())
        ;
        responseHandler(ctx, restCall, res);
      });
  }

  // DataBase Service Methods
  // Collection Methods
  // GET -> Show
  @Override
  public void showCollection(RoutingContext ctx, Vertx vertx, DatabaseService service, Database db, String restCall) {
    service.showCollectionRecords(db, vertx)
      .map(JsonArray::new)
      .onSuccess(res -> {
        res = emptyResultHandler(res, "Collection does not have any records in Database");
        responseHandler(ctx, restCall, res);
      });
  }

  // DELETE -> Delete
  @Override
  public void deleteCollection(RoutingContext ctx, Vertx vertx, DatabaseService service, Database db, String restCall) {
    service.deleteCollection(db, vertx)
      .map(res -> new JsonArray())
      .onSuccess(res -> {
        res.add(new JsonObject()
          .put("rest_call", restCall)
          .put("collection_name", db.getCollectionName())
        );
        responseHandler(ctx, restCall, res);
      })
    ;
  }

  // POST -> Insert
  @Override
  public void insertCollection(RoutingContext ctx, Vertx vertx, DatabaseService service, Database db, String restCall) {
    service.insertCollection(db, vertx)
      .map(res -> new JsonArray())
      .onSuccess(res -> {
        res.add(new JsonObject()
          .put("rest_call", restCall)
          .put("collection_name", db.getCollectionName())
        );
        responseHandler(ctx, restCall, res);
      });
  }

  // Database Methods
  // GET -> Show
  @Override
  public void showDatabase(RoutingContext ctx, Vertx vertx, DatabaseService service, Database db, String restCall) {
    service.showCollections(db, vertx)
      .map(res -> {
        JsonArray jsonArray = new JsonArray();
        for (String str:res) {
          jsonArray.add(new JsonObject().put("collection_name", str));
        }
        return jsonArray;
      })
      .onSuccess(res -> {
        res = emptyResultHandler(res, "No Collections in Database");
        responseHandler(ctx, restCall, res);
      });
  }

  private static void responseHandler(RoutingContext ctx, String restCall, JsonArray res) {
    log.debug("PATH: {}", ctx.normalizedPath());
    log.debug("REST CALL: {}", restCall);
    log.debug("RESPONSE: {}", res.encodePrettily());
    ctx.response().end(res.encodePrettily());
  }

  private static JsonArray emptyResultHandler(JsonArray res, String msg) {
    if (res.contains(null)){
      res = new JsonArray()
        .add(new JsonObject()
          .put("Message", msg)
        );
    }
    return res;
  }
}
