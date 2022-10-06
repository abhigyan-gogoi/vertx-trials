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
  private final EmployeeInterface employee;
  private final EmployeeService employeeService;
  private final NewEmployee newEmployee;

  public RouteGenHandlerImpl(Database db, Router router, Vertx vertx){
    this.router = router;
    this.db = db;
    this.vertx = vertx;
    this.employee = new EmployeeImpl();
    this.newEmployee = new NewEmployeeImpl();
    this.employeeService = new EmployeeServiceImpl();
//    // Mount handler for specific incoming requests
//    router.route("/").handler(this::landingHandler);
//    // Setup and Calling next() in handler
//    router.route("/routes/next-handler").handler(this::nextHandler);
//    router.route("/routes/next-handler").handler(this::nextNextHandler);
//    router.route("/routes/next-handler").handler(this::nextEndHandler);
//    // Different Routing methods
//    router.route("/routes/exact-path").handler(this::routingExactPath);
//    router.route("/routes/begin-something/*").handler(this::routingBeginSomething);
//    router.route().pathRegex(".*reg").handler(this::routingRegularExpressions);
//    router.route("/routes/:param1/:param2").handler(this::routingParam1);
//    router.route("/routes/:param1-:param2").handler(this::routingParam2);
//    router.routeWithRegex(".*routes/reg-alt").handler(this::routingRegularExpressionsAlt);
//    router.routeWithRegex(".*routes/reg-params").pathRegex("\\/([^\\/]+)\\/([^\\/]+)").handler(this::routingRegularParam1);
//    // Redirect to a new URL
//    router.route("/redirect/vertx/java-doc").handler(this::redirectURL);
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
    this.newEmployee.set_id(routingContext.pathParam("ID"));
    this.employeeService.showEmployee(this.db, this.newEmployee, vertx)
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
    this.newEmployee.setLast_name(routingContext.pathParam("LastName"));
    String NewLastName = routingContext.pathParam("NewLastName");
    this.employeeService.updateEmployee(this.db, this.newEmployee, NewLastName, vertx)
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
    this.newEmployee.set_id(routingContext.pathParam("ID"));
    this.employeeService.deleteEmployee(this.db, this.newEmployee, vertx)
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
    this.newEmployee.set_id(routingContext.pathParam("ID"));
    this.employeeService.insertEmployee(this.db, this.newEmployee, vertx)
      .map(res -> new JsonArray())
      .onSuccess(res -> {
        res = new JsonArray()
          .add(this.newEmployee.getEmployeeJson())
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
    this.employee.getMongoDao().showCollection(this.db);
    routingContext.json(
      new JsonObject()
        .put("Page", "GET request to MongoDB")
        .put("DatabaseName", this.db.getDatabaseName())
        .put("CollectionName", this.db.getCollectionName())
    );
  }

  private void mongoGetAllCollections(RoutingContext routingContext) {
    this.db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    this.employee.getMongoDao().showDatabaseCollections(this.db);
    routingContext.json(
      new JsonObject()
        .put("Page", "GET request for all MongoDB Collections")
        .put("DatabaseName", this.db.getDatabaseName())
    );
  }

//  // Redirection example
//  private void redirectURL(RoutingContext routingContext) {
//    routingContext.redirect("https://vertx.io/docs/vertx-web/java/");
//  }
//
//  // Routing examples
//  private void routingRegularParam1(RoutingContext routingContext) {
//    // This regular expression matches paths that start with something like:
//    // "/reg-params/bar" - where the "reg-params" is captured into param0 and the "bar" is
//    // captured into param1
//    String param0 = routingContext.pathParam("param0");
//    String param1 = routingContext.pathParam("param1");
//    // JSON response
//    routingContext.json(
//      new JsonObject()
//        .put("Page", "Routing with Regular Expressions by capturing path parameters")
//        .put("param0", param0)
//        .put("param1", param1)
//    );
//  }
//
//  private void routingRegularExpressionsAlt(RoutingContext routingContext) {
//    // This handler will be called for:
//    // /some/path/reg-alt
//    // /reg-alt
//    // /reg-alt/bar/wibble/reg-alt
//    // /bar/reg-alt
//    // But not:
//    // /bar/wibble
//    // JSON response
//    routingContext.json(
//      new JsonObject()
//        .put("Page", "Routing (Alternate: using pathRegex) by Regular Expressions")
//    );
//  }
//
//  private void routingParam2(RoutingContext routingContext) {
//    String param1 = routingContext.pathParam("param1");
//    String param2 = routingContext.pathParam("param2");
//    // JSON response
//    routingContext.json(
//      new JsonObject()
//        .put("Page", "Routing by capturing path parameters 2")
//        .put("param1", param1)
//        .put("param2", param2)
//    );
//  }
//
//  private void routingParam1(RoutingContext routingContext) {
//    String param1 = routingContext.pathParam("param1");
//    String param2 = routingContext.pathParam("param2");
//    // JSON response
//    routingContext.json(
//      new JsonObject()
//        .put("Page", "Routing by capturing path parameters 1")
//        .put("param1", param1)
//        .put("param2", param2)
//    );
//  }
//
//  private void routingRegularExpressions(RoutingContext routingContext) {
//    // This handler will be called for:
//    // /some/path/reg
//    // /reg
//    // /reg/bar/wibble/reg
//    // /bar/reg
//    // But not:
//    // /bar/wibble
//    // JSON response
//    routingContext.json(
//      new JsonObject()
//        .put("Page", "Routing by Regular Expressions")
//    );
//  }
//
//  private void routingBeginSomething(RoutingContext routingContext) {
//    // JSON response
//    routingContext.json(
//      new JsonObject()
//        .put("Page", "Routing by Exact Path")
//        .put("Paths", "Starts with 'routing/begin-something/*")
//    );
//  }
//
//  private void routingExactPath(RoutingContext routingContext) {
//    // JSON response
//    routingContext.json(
//      new JsonObject()
//        .put("Page", "Routing by Exact Path")
//    );
//  }
//
//  private void nextEndHandler(RoutingContext routingContext) {
//    HttpServerResponse response = routingContext.response();
//    // String response
//    response.write("Calling end()\n");
//    // Now end the response
//    routingContext.response( ).end();
//  }
//
//  private void nextNextHandler(RoutingContext routingContext) {
//    HttpServerResponse response = routingContext.response();
//    // String response
//    response.write("Calling next()\n");
//    // Call the next matching route after a 1-second delay
//    routingContext.vertx().setTimer(3000, tid -> routingContext.next());
//  }
//
//  private void nextHandler(RoutingContext routingContext) {
//    HttpServerResponse response = routingContext.response();
//    // enable chunked responses because we will be adding data as
//    // we execute over other handlers. This is only required once and
//    // only if several handlers do output.
//    response.setChunked(true);
//    // String response
//    response.write("Calling next()\n");
//    // Call the next matching route after a 1-second delay
//    routingContext.vertx().setTimer(3000, tid -> routingContext.next());
//  }
//
//  private void landingHandler(RoutingContext routingContext) {
//    // Get address of request
//    String address = routingContext.request().connection().remoteAddress().toString();
//    // Get the query parameter "name"
//    MultiMap queryParams = routingContext.queryParams();
//    String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
//    // JSON response
//    routingContext.json(
//      new JsonObject()
//        .put("name", name)
//        .put("address", address)
//        .put("message", "Hello "+name+" connected from Vertx Web! Address:"+address)
//    );
//  }

}
