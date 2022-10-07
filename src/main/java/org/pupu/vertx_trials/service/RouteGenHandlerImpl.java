package org.pupu.vertx_trials.service;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.pupu.vertx_trials.model.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class RouteGenHandlerImpl implements RouteGenHandler{
//  private static final Logger log = LoggerFactory.getLogger(RouteGenHandlerImpl.class);
  private final Router router;
  private final Database db;
  private final Vertx vertx;
  private final Async async;
  private final Employee employee;
  private final DatabaseService dbService;
  private final EmployeeService employeeService;

  public RouteGenHandlerImpl(Database db, Router router, Vertx vertx){
    this.router = router;
    this.db = db;
    this.vertx = vertx;
    this.async = new AsyncImpl();
    this.employee = new EmployeeImpl();
    this.employeeService = new EmployeeServiceImpl();
    this.dbService = new DatabaseServiceImpl();
    // Mount handler for specific incoming requests
    // MongoDB routes
    router.get("/mongo/:DatabaseName").handler(this::mongoGetDatabase);
    router.get("/mongo/:DatabaseName/:CollectionName").handler(this::mongoGetCollection);
    router.get("/mongo/:DatabaseName/:CollectionName/:ID").handler(this::mongoGet);
    router.post("/mongo/:DatabaseName/:CollectionName").handler(this::mongoPostCollection);
    router.post("/mongo/:DatabaseName/:CollectionName/:ID/:FirstName/:LastName").handler(this::mongoPost);
    router.delete("/mongo/:DatabaseName/:CollectionName/:ID").handler(this::mongoDelete);
    router.delete("/mongo/:DatabaseName/:CollectionName").handler(this::mongoDeleteCollection);
    router.put("/mongo/:DatabaseName/:CollectionName/:ID/:FirstName/:LastName").handler(this::mongoPut);
  }

  @Override
  public void handle(RoutingContext routingContext) {
    router.handleContext(routingContext);
  }

  // Employee Service (Record/Document) Methods
  // GET -> Show
  private void mongoGet(RoutingContext routingContext) {
    String restCall = "GET";
    db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    db.setCollectionName(routingContext.pathParam("CollectionName"));
    employee.set_id(routingContext.pathParam("ID"));
    async.showEmployee(routingContext, vertx, employeeService, db, employee, restCall);
  }

  // PUT -> Update
  private void mongoPut(RoutingContext routingContext) {
    String restCall = "PUT";
    db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    db.setCollectionName(routingContext.pathParam("CollectionName"));
    employee.set_id(routingContext.pathParam("ID"));
    employee.setFirst_name(routingContext.pathParam("FirstName"));
    employee.setLast_name(routingContext.pathParam("LastName"));
    async.updateEmployee(routingContext, vertx, employeeService, db, employee, restCall);
  }

  // DELETE -> Delete
  private void mongoDelete(RoutingContext routingContext) {
    String restCall = "DELETE";
    db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    db.setCollectionName(routingContext.pathParam("CollectionName"));
    employee.set_id(routingContext.pathParam("ID"));
    async.deleteEmployee(routingContext, vertx, employeeService, db, employee, restCall);
  }

  // POST -> Insert
  private void mongoPost(RoutingContext routingContext) {
    String restCall = "POST";
    db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    db.setCollectionName(routingContext.pathParam("CollectionName"));
    employee.set_id(routingContext.pathParam("ID"));
    employee.setFirst_name(routingContext.pathParam("FirstName"));
    employee.setLast_name(routingContext.pathParam("LastName"));
    async.insertEmployee(routingContext, vertx, employeeService, db, employee, restCall);
  }

  // Collection Methods
  // GET -> Show
  private void mongoGetCollection(RoutingContext routingContext) {
    String restCall = "GET";
    db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    db.setCollectionName(routingContext.pathParam("CollectionName"));
    async.showCollection(routingContext, vertx, dbService, db, restCall);
  }


  // DELETE -> Delete
  private void mongoDeleteCollection(RoutingContext routingContext) {
    String restCall = "DELETE";
    db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    db.setCollectionName(routingContext.pathParam("CollectionName"));
    async.deleteCollection(routingContext, vertx, dbService, db, restCall);
  }

  // POST -> Insert
  private void mongoPostCollection(RoutingContext routingContext) {
    String restCall = "POST";
    db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    db.setCollectionName(routingContext.pathParam("CollectionName"));
    async.insertCollection(routingContext, vertx, dbService, db, restCall);
  }

  // Database Methods
  // GET -> Show
  private void mongoGetDatabase(RoutingContext routingContext) {
    String restCall = "GET";
    db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    async.showDatabase(routingContext, vertx, dbService, db, restCall);
  }

}
