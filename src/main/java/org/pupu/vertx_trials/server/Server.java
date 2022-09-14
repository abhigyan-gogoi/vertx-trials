package org.pupu.vertx_trials.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

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
    // Calling next in handler
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

    router.route("/next-handler/").handler(context -> {
      HttpServerResponse response = context.response();
      // String response
      response.write("Calling next()\n");
      // Call the next matching route after a 1-second delay
      context.vertx().setTimer(3000, tid -> context.next());
    });

    router.route("/next-handler/").handler(context -> {
      HttpServerResponse response = context.response();
      // String response
      response.write("Calling end()\n");
      // Now end the response
      context.response( ).end();
    });

    // Start server on port 8888
    server
      // Router to handle every request
      .requestHandler(router)
      // Start listening on port 8888
      .listen(8888)
      // Print connection port
      .onSuccess(httpServer -> {
        System.out.println("HTTP server started on port: " + httpServer.actualPort());
        /*

         */
        }
      );

  }
}
