package org.pupu.vertx_trials.service;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.pupu.vertx_trials.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteGenHandlerImpl implements RouteGenHandler{
  private static final Logger log = LoggerFactory.getLogger(RouteGenHandlerImpl.class);
  private final Router router;
  private final Database db;
  private final Vertx vertx;
  private final EmployeeService employeeService;
  private final Employee employee;

  public RouteGenHandlerImpl(Database db, Router router, Vertx vertx){
    this.router = router;
    this.db = db;
    this.vertx = vertx;
    this.employee = new EmployeeImpl();
    this.employeeService = new EmployeeServiceImpl();
    // Mount handler for specific incoming requests
    // MongoDB routes
    router.get("/mongo/all-collections/:DatabaseName").handler(this::mongoGetAllCollections);
    router.get("/mongo/:DatabaseName/:CollectionName").handler(this::mongoGetCollection);
    router.get("/mongo/:DatabaseName/:CollectionName/:ID").handler(this::mongoGet);
    router.post("/mongo/:DatabaseName/:CollectionName").handler(this::mongoPostCollection);
    router.post("/mongo/:DatabaseName/:CollectionName/:ID").handler(this::mongoPost);
    router.delete("/mongo/:DatabaseName/:CollectionName/:ID").handler(this::mongoDelete);
    router.delete("/mongo/:DatabaseName/:CollectionName/:ID").handler(this::mongoDelete);
    router.put("/mongo/:DatabaseName/:CollectionName/:LastName/:NewLastName").handler(this::mongoPut);

  }

  private void mongoPostCollection(RoutingContext routingContext) {
    // TODO
  }

  @Override
  public void handle(RoutingContext routingContext) {
    router.handleContext(routingContext);
  }

  private void mongoGet(RoutingContext routingContext) {
    String restCall = "GET";
    this.db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    this.db.setCollectionName(routingContext.pathParam("CollectionName"));
    this.employee.set_id(routingContext.pathParam("ID"));
    this.employeeService.showEmployee(this.db, this.employee, vertx)
      .map(res -> new JsonArray().add(res))
      .onSuccess(res -> {
        if (res.contains(null)){
          res = new JsonArray()
            .add(new JsonObject()
              .put("Message", "ID does not exist in Database")
            )
          ;
        }
        log.debug("PATH: {}", routingContext.normalizedPath());
        log.debug("REST CALL: {}", restCall);
        log.debug("RESPONSE: {}", res.encodePrettily());
        routingContext.response().end(res.encodePrettily());
      })
    ;
  }

  private void mongoPut(RoutingContext routingContext) {
    String restCall = "PUT";
    this.db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    this.db.setCollectionName(routingContext.pathParam("CollectionName"));
    this.employee.setLast_name(routingContext.pathParam("LastName"));
    String NewLastName = routingContext.pathParam("NewLastName");
    this.employeeService.updateEmployee(this.db, this.employee, NewLastName, vertx)
      .map(res -> new JsonArray().add(res))
      .onSuccess(res -> {
        if (res.contains(null)){
          res = new JsonArray()
            .add(new JsonObject()
              .put("Message", "ID does not exist in Database")
            )
          ;
        }
        log.debug("PATH: {}", routingContext.normalizedPath());
        log.debug("REST CALL: {}", restCall);
        log.debug("RESPONSE: {}", res.encodePrettily());
        routingContext.response().end(res.encodePrettily());
      })
    ;
  }

  private void mongoDelete(RoutingContext routingContext) {
    String restCall = "DELETE";
    this.db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    this.db.setCollectionName(routingContext.pathParam("CollectionName"));
    this.employee.set_id(routingContext.pathParam("ID"));
    this.employeeService.deleteEmployee(this.db, this.employee, vertx)
      .map(res -> new JsonArray().add(res))
      .onSuccess(res -> {
        if (res.contains(null)){
          res = new JsonArray()
            .add(new JsonObject()
              .put("Message", "ID does not exist in Database")
            )
          ;
        }
        log.debug("PATH: {}", routingContext.normalizedPath());
        log.debug("REST CALL: {}", restCall);
        log.debug("RESPONSE: {}", res.encodePrettily());
        routingContext.response().end(res.encodePrettily());
      })
    ;
  }

  private void mongoPost(RoutingContext routingContext) {
    String restCall = "POST";
    this.db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    this.db.setCollectionName(routingContext.pathParam("CollectionName"));
    this.employee.set_id(routingContext.pathParam("ID"));
    this.employeeService.insertEmployee(this.db, this.employee, vertx)
      .map(res -> new JsonArray())
      .onSuccess(res -> {
        res = new JsonArray()
          .add(this.employee.getEmployeeJson())
        ;
        log.debug("PATH: {}", routingContext.normalizedPath());
        log.debug("REST CALL: {}", restCall);
        log.debug("RESPONSE: {}", res.encodePrettily());
        routingContext.response().end(res.encodePrettily());
      })
    ;
  }

  private void mongoGetCollection(RoutingContext routingContext) {
    this.db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    this.db.setCollectionName(routingContext.pathParam("CollectionName"));
//    this.employee.getMongoDao().showCollection(this.db);
    routingContext.json(
      new JsonObject()
        .put("Page", "GET request to MongoDB")
        .put("DatabaseName", this.db.getDatabaseName())
        .put("CollectionName", this.db.getCollectionName())
    );
  }

  private void mongoGetAllCollections(RoutingContext routingContext) {
    this.db.setDatabaseName(routingContext.pathParam("DatabaseName"));
//    this.employee.getMongoDao().showDatabaseCollections(this.db);
    routingContext.json(
      new JsonObject()
        .put("Page", "GET request for all MongoDB Collections")
        .put("DatabaseName", this.db.getDatabaseName())
    );
  }

}
