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
      .onSuccess(res -> successHandler(res, "ID does NOT exist in Database", ctx, restCall));
  }

  // PUT -> Update
  @Override
  public void updateEmployee(RoutingContext ctx, Vertx vertx, EmployeeService service, Database db, Employee employee, String restCall) {
    service.updateEmployee(db, employee, vertx)
      .map(res -> new JsonArray().add(res))
      .onSuccess(res -> {
        if (res.contains(null)){
          res = new JsonArray()
            .add(new JsonObject()
              .put("Message", "ID does not exist in Database")
            );
        }
        res
          .add(new JsonObject()
            .put("Message", "Replaced above record with the one below"))
          .add(employee.getEmployeeJson());
        log.debug("PATH: {}", ctx.normalizedPath());
        log.debug("REST CALL: {}", restCall);
        log.debug("RESPONSE: {}", res.encodePrettily());
        ctx.response().end(res.encodePrettily());
      });
  }

  // DELETE -> Delete
  @Override
  public void deleteEmployee(RoutingContext ctx, Vertx vertx, EmployeeService service, Database db, Employee employee, String restCall) {
    service.deleteEmployee(db, employee, vertx)
      .map(res -> new JsonArray().add(res))
      .onSuccess(res -> successHandler(res, "ID does not exist in Database", ctx, restCall));
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
        log.debug("PATH: {}", ctx.normalizedPath());
        log.debug("REST CALL: {}", restCall);
        log.debug("RESPONSE: {}", res.encodePrettily());
        ctx.response().end(res.encodePrettily());
      });
  }

  // Collection Methods
  // GET -> Show
  @Override
  public void showCollection(RoutingContext ctx, Vertx vertx, DatabaseService service, Database db, String restCall) {
    service.showCollectionRecords(db, vertx)
      .map(JsonArray::new)
      .onSuccess(res -> successHandler(res, "Collection does not have any records in Database", ctx, restCall));
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
        log.debug("PATH: {}", ctx.normalizedPath());
        log.debug("REST CALL: {}", restCall);
        log.debug("RESPONSE: {}", res.encodePrettily());
        ctx.response().end(res.encodePrettily());
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
        log.debug("PATH: {}", ctx.normalizedPath());
        log.debug("REST CALL: {}", restCall);
        log.debug("RESPONSE: {}", res.encodePrettily());
        ctx.response().end(res.encodePrettily());
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
      .onSuccess(res -> successHandler(res, "No Collections in Database", ctx, restCall));
  }

  private static void successHandler(JsonArray res, String message, RoutingContext ctx, String restCall) {
    if (res.contains(null)){
      res = new JsonArray()
        .add(new JsonObject()
          .put("Message", message)
        )
      ;
    }
    log.debug("PATH: {}", ctx.normalizedPath());
    log.debug("REST CALL: {}", restCall);
    log.debug("RESPONSE: {}", res.encodePrettily());
    ctx.response().end(res.encodePrettily());
  }
}
