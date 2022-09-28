package org.pupu.vertx_trials.service;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.model.EmployeeImpl;
import org.pupu.vertx_trials.model.EmployeeInterface;

public class RouteGenHandlerImpl implements RouteGenHandler{

  private final Router router;
  private final Vertx vertx;
  private final Database db;
  private final EmployeeInterface employee;

  public RouteGenHandlerImpl(Database db, Vertx vertx, Router router){
    this.router = router;
    this.vertx = vertx;
    this.db = db;
    this.employee = new EmployeeImpl();
    // Mount handler for specific incoming requests
    router.route("/").handler(this::landingHandler);
    // Setup and Calling next() in handler
    router.route("/routes/next-handler").handler(this::nextHandler);
    router.route("/routes/next-handler").handler(this::nextNextHandler);
    router.route("/routes/next-handler").handler(this::nextEndHandler);
    // Different Routing methods
    router.route("/routes/exact-path").handler(this::routingExactPath);
    router.route("/routes/begin-something/*").handler(this::routingBeginSomething);
    router.route().pathRegex(".*reg").handler(this::routingRegularExpressions);
    router.route("/routes/:param1/:param2").handler(this::routingParam1);
    router.route("/routes/:param1-:param2").handler(this::routingParam2);
    router.routeWithRegex(".*routes/reg-alt").handler(this::routingRegularExpressionsAlt);
    router.routeWithRegex(".*routes/reg-params").pathRegex("\\/([^\\/]+)\\/([^\\/]+)").handler(this::routingRegularParam1);
    // Redirect to a new URL
    router.route("/redirect/vertx/java-doc").handler(this::redirectURL);
    // MongoDB routes
    router.get("/mongo/all-collections/:DatabaseName").handler(this::mongoGetAllCollections);
    router.get("/mongo/:DatabaseName/:CollectionName").handler(this::mongoGetCollection);
    router.post("/mongo/:DatabaseName/:CollectionName").handler(this::mongoPost);
    router.delete("/mongo/:DatabaseName/:CollectionName/:ID").handler(this::mongoDelete);
    router.put("/mongo/:DatabaseName/:CollectionName/:ID/:NewID").handler(this::mongoPut);

  }

  @Override
  public void handle(RoutingContext routingContext) {
    router.handleContext(routingContext);
  }

  private void mongoPut(RoutingContext routingContext) {
//    String DatabaseName = routingContext.pathParam("DatabaseName");
//    String CollectionName = routingContext.pathParam("CollectionName");
//    String ID = routingContext.pathParam("ID");
//    String NewID = routingContext.pathParam("NewID");
//    // Start a MongoClient which POSTs to specified DB and Collection
//    vertx.deployVerticle(new MongoPut(dbUri, DatabaseName, CollectionName, ID, NewID));
//    routingContext.json(
//      new JsonObject()
//        .put("Page", "PUT request to MongoDB")
//        .put("DatabaseName", DatabaseName)
//        .put("CollectionName", CollectionName)
//    );
  }

  private void mongoDelete(RoutingContext routingContext) {
  }

  private void mongoPost(RoutingContext routingContext) {
    this.db.setDatabaseName(routingContext.pathParam("DatabaseName"));
    this.db.setCollectionName(routingContext.pathParam("CollectionName"));
    // Start a MongoClient which POSTs to specified DB and Collection
//    vertx.deployVerticle(new MongoPost(dbUri, DatabaseName, CollectionName));
    // TODO
    this.employee.getMongoDao().insertRecord(this.db, this.employee.getEmployeeJson());
    routingContext.json(
      new JsonObject()
        .put("Page", "POST request to MongoDB")
        .put("DatabaseName", this.db.getDatabaseName())
        .put("CollectionName", this.db.getCollectionName())
    );
  }
  private void mongoGetCollection(RoutingContext routingContext) {
  }

  private void mongoGetAllCollections(RoutingContext routingContext) {
  }

  private void redirectURL(RoutingContext routingContext) {
  }

  private void routingRegularParam1(RoutingContext routingContext) {
  }

  private void routingRegularExpressionsAlt(RoutingContext routingContext) {
  }

  private void routingParam2(RoutingContext routingContext) {
  }

  private void routingParam1(RoutingContext routingContext) {
  }

  private void routingRegularExpressions(RoutingContext routingContext) {
  }

  private void routingBeginSomething(RoutingContext routingContext) {
  }

  private void routingExactPath(RoutingContext routingContext) {
  }

  private void nextEndHandler(RoutingContext routingContext) {
  }

  private void nextNextHandler(RoutingContext routingContext) {
  }

  private void nextHandler(RoutingContext routingContext) {
  }

  private void landingHandler(RoutingContext routingContext) {
  }

}
