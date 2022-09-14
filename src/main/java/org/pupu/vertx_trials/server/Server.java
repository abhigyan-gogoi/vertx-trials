package org.pupu.vertx_trials.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class Server extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    // Create HTTP server
    HttpServer server = vertx.createHttpServer();
    // Create a router
    Router router = Router.router(vertx);
    // Mount handler for all incoming requests
    // At every path and HTTP method
    router.route().handler(context -> {
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
          .put("message", "Hello "+name+" connected from "+address)
      );
    });

    server
      // Router to handle every request
      .requestHandler(router)
      // Start listening on port 8888
      .listen(8888)
      // Print connection port
      .onSuccess(httpServer ->
        System.out.println("HTTP server started on port: "+httpServer.actualPort()
        )
      );
  }
}
