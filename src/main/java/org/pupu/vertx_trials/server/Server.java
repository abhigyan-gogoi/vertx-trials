package org.pupu.vertx_trials.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Server extends AbstractVerticle {
  private final String output = "Temperature recorded : ";
  private static final int httpPort = Integer.parseInt(System.getenv()
    .getOrDefault("HTTP_PORT", "8888"));
  private final String uuid = UUID.randomUUID().toString();
  private double temp = 21.0;
  private final Random random = new Random();

  @Override
  public void start(Promise<Void> startPromise) {
    vertx.setPeriodic(2000, this::updateTemperatureData);

    // Create HTTP server
    HttpServer server = vertx.createHttpServer();
    // Create a router
    Router router = Router.router(vertx);

    // Mount handler for specific incoming requests
    // At '/' path and HTTP method
    router.route("/").handler(this::landingHandler);

    // Setup and Calling next() in handler
    router.route("/next-handler/").handler(this::nextHandler);
    // Calling next() in handler
    router.route("/next-handler/").handler(this::nextNextHandler);
    // Calling end() in handler
    router.route("/next-handler/").handler(this::nextEndHandler);

    // Different Routing methods
    router
      .route("/routing/exact-path/")
      .handler(this::routingExactPath);
    router
      .route("/routing/begin-something/*")
      .handler(this::routingBeginSomething);
    router
      .route()
      .pathRegex(".*reg")
      .handler(this::routingRegularExpressions);
    router
      .routeWithRegex(".*reg-alt")
      .handler(this::routingRegularExpressionsAlt);
    router
      .routeWithRegex(".*reg-params")
      .pathRegex("\\/([^\\/]+)\\/([^\\/]+)")
      .handler(this::routingRegularParam1);
    router
      .route("/routing/:param1/:param2/")
      .handler(this::routingParam1);
    router
      .route("/routing/:param1-:param2/")
      .handler(this::routingParam2);

    // Redirect to a new URL
    router.route("/redirect/").handler(this::redirectURL);

    // Record temperature Data
    router.route("/temp/").handler(this::getTemperatureData);

    // Start server on port 8888
    server
      // Router to handle every request
      .requestHandler(router)
      // Start listening on port 8888
      .listen(httpPort)
      // Print connection port
      .onSuccess(httpServer -> {
        System.out.println("HTTP server started on port: " + httpServer.actualPort());
        startPromise.complete();
        }
      )
      .onFailure(startPromise::fail);

  }

  // Method updates temperature randomly
  private void updateTemperatureData(Long id) {
    temp = temp+(random.nextGaussian() / 2.0d);
    System.out.println(output+temp);

    vertx.eventBus()
      .publish("Temperature.updates", createPayload());
  }

  // Method to Record temperature Data
  private void getTemperatureData(RoutingContext routingContext) {
    System.out.println("Processing HTTP request from " +
      routingContext.request().remoteAddress());
    JsonObject payload = createPayload();
    routingContext.response()
      .putHeader("Content-type", "app/json")
      .setStatusCode(200)
      .end(payload.encodePrettily());
  }

  // Method to generate JSON payload with uuid, temperature and timestamp
  private JsonObject createPayload() {
    long millisec = System.currentTimeMillis();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
    Date date = new Date(millisec);
    return new JsonObject()
      .put("uuid", uuid)
      .put("temperature", temp)
      .put("timestamp", dateFormat.format(date));
  }

  // Method returns JSON response
  private void landingHandler(RoutingContext routingContext) {
    // Get address of request
    String address = routingContext.request().connection().remoteAddress().toString();
    // Get the query parameter "name"
    MultiMap queryParams = routingContext.queryParams();
    String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";

    // JSON response
    routingContext.json(
      new JsonObject()
        .put("name", name)
        .put("address", address)
        .put("message", "Hello "+name+" connected from Vertx Web! Address:"+address)
    );
  }

  // Methods showing Setup and Calling next() in handler
  private void nextHandler(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();

    // enable chunked responses because we will be adding data as
    // we execute over other handlers. This is only required once and
    // only if several handlers do output.
    response.setChunked(true);
    // String response
    response.write("Calling next()\n");
    // Call the next matching route after a 1-second delay
    routingContext.vertx().setTimer(3000, tid -> routingContext.next());
  }

  // Method Calling next() in handler
  private void nextNextHandler(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    // String response
    response.write("Calling next()\n");
    // Call the next matching route after a 1-second delay
    routingContext.vertx().setTimer(3000, tid -> routingContext.next());
  }

  // Method Calling end() in handler
  private void nextEndHandler(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    // String response
    response.write("Calling end()\n");
    // Now end the response
    routingContext.response( ).end();
  }

  // Method for Routing by exact path
  private void routingExactPath(RoutingContext routingContext) {
    // JSON response
    routingContext.json(
      new JsonObject()
        .put("Page", "Routing by Exact Path")
    );
  }

  // Method for Routing by path that begins with something
  private void routingBeginSomething(RoutingContext routingContext) {
    // JSON response
    routingContext.json(
      new JsonObject()
        .put("Page", "Routing by Exact Path")
        .put("Paths", "Starts with 'routing/begin-something/*")
      );
  }

  // Method for Routing by capturing path parameters 1
  private void routingParam1(RoutingContext routingContext) {
    String param1 = routingContext.pathParam("param1");
    String param2 = routingContext.pathParam("param2");
    // JSON response
    routingContext.json(
      new JsonObject()
        .put("Page", "Routing by capturing path parameters 1")
        .put("param1", param1)
        .put("param2", param2)
    );
  }

  // Method for Routing by capturing path parameters 2
  private void routingParam2(RoutingContext routingContext) {
    String param1 = routingContext.pathParam("param1");
    String param2 = routingContext.pathParam("param2");
    // JSON response
    routingContext.json(
      new JsonObject()
        .put("Page", "Routing by capturing path parameters 2")
        .put("param1", param1)
        .put("param2", param2)
    );
  }

  // Method for routing using regular expressions
  private void routingRegularExpressions(RoutingContext routingContext) {
    // This handler will be called for:
    // /some/path/reg
    // /reg
    // /reg/bar/wibble/reg
    // /bar/reg
    // But not:
    // /bar/wibble
    // JSON response
    routingContext.json(
      new JsonObject()
        .put("Page", "Routing by Regular Expressions")
    );
  }

  // Method (Alternate) for routing using regular expressions
  private void routingRegularExpressionsAlt(RoutingContext routingContext) {
    // This handler will be called for:
    // /some/path/reg-alt
    // /reg-alt
    // /reg-alt/bar/wibble/reg-alt
    // /bar/reg-alt
    // But not:
    // /bar/wibble
    // JSON response
    routingContext.json(
      new JsonObject()
        .put("Page", "Routing (Alternate: using pathRegex) by Regular Expressions")
    );
  }

  // Method for routing using Regular Expressions by capturing parameters
  private void routingRegularParam1(RoutingContext routingContext) {
    // This regular expression matches paths that start with something like:
    // "/reg-params/bar" - where the "reg-params" is captured into param0 and the "bar" is
    // captured into param1
    String param0 = routingContext.pathParam("param0");
    String param1 = routingContext.pathParam("param1");
    // JSON response
    routingContext.json(
      new JsonObject()
        .put("Page", "Routing with Regular Expressions by capturing path parameters")
        .put("param0", param0)
        .put("param1", param1)
    );
  }

  // Method to redirect to a new URL
  private void redirectURL(RoutingContext routingContext) {
    routingContext.redirect("https://vertx.io/docs/vertx-web/java/");
  }
}

