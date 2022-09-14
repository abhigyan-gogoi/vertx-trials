package org.pupu.vertx_trials.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class Server extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    // Create HTTP server
    HttpServer server = vertx.createHttpServer();
    // Create a router
    Router router = Router.router(vertx);

    // Mount handler for specific incoming requests
    // At '/' path and HTTP method
    router.route("/").handler(context -> {
      // Get address of request
      String address = context.request().connection().remoteAddress().toString();
      // Get the query parameter "name"
      MultiMap queryParams = context.queryParams();
      String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";

      // JSON response
      context.json(
        new JsonObject()
          .put("name", name)
          .put("address", address)
          .put("message", "Hello "+name+" connected from Vertx Web! Address:"+address)
      );
    });
    // At '/next-handler/' path
    // Setup and Calling next() in handler
    router.route("/next-handler/").handler(context -> {
      HttpServerResponse response = context.response();

      // enable chunked responses because we will be adding data as
      // we execute over other handlers. This is only required once and
      // only if several handlers do output.
      response.setChunked(true);
      // String response
      response.write("Calling next()\n");
      // Call the next matching route after a 1-second delay
      context.vertx().setTimer(3000, tid -> context.next());
    });
    // Calling next() in handler
    router.route("/next-handler/").handler(context -> {
      HttpServerResponse response = context.response();
      // String response
      response.write("Calling next()\n");
      // Call the next matching route after a 1-second delay
      context.vertx().setTimer(3000, tid -> context.next());
    });
    // Calling end() in handler
    router.route("/next-handler/").handler(context -> {
      HttpServerResponse response = context.response();
      // String response
      response.write("Calling end()\n");
      // Now end the response
      context.response( ).end();
    });

    // Routing by exact path
    router.route("/routing/exact-path/").handler(this::routingExactPath);

    // Routing by path that begins with something
    router.route("/routing/begin-something/*").handler(this::routingBeginSomething);

    // Routing by capturing path parameters 1
    router.route("/routing/:param1-:param2/").handler(this::routingParam1);

    // Routing by capturing path parameters 2
    router.route("/routing/:param1-:param2/").handler(this::routingParam2);

    // Redirect to a new URL
    router.route("/redirect/").handler(this::redirectURL);


    // Start server on port 8888
    server
      // Router to handle every request
      .requestHandler(router)
      // Start listening on port 8888
      .listen(8888)
      // Print connection port
      .onSuccess(httpServer -> {
        System.out.println("HTTP server started on port: " + httpServer.actualPort());
        }
      );

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

  // Method to redirect to a new URL
  private void redirectURL(RoutingContext routingContext) {
    routingContext.redirect("https://vertx.io/docs/vertx-web/java/");
  }
}
