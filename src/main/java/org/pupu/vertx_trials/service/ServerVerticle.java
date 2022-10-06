package org.pupu.vertx_trials.service;

import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.pupu.vertx_trials.model.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerVerticle extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(ServerVerticle.class);
  private static final int httpPort = Integer.parseInt(System.getenv()
    .getOrDefault("HTTP_PORT", "8888"));
  private static final String dbUri = "mongodb://localhost:27017";

  @Override
  public void start(Promise<Void> startPromise) {
//    vertx.setPeriodic(2000, this::updateTemperatureData);
    // Create Database object
    DatabaseConfig db = new DatabaseConfig();
    db.setDbUri(dbUri);
    // Create HTTP server
    HttpServer server = vertx.createHttpServer();
    // Create a router context handler using RouteGenHandler interface
    Router router = Router.router(vertx);
    new RouteGenHandlerImpl(db, router, vertx);
    server
      // Router to handle every request
      .requestHandler(router)
      // Start listening on port 8888
      .listen(httpPort)
      // Print connection port
      .onSuccess(httpServer -> {
        log.debug("HTTP server started on port: {}", httpServer.actualPort());
        startPromise.complete();
        }
      )
      .onFailure(startPromise::fail);
  }
}

