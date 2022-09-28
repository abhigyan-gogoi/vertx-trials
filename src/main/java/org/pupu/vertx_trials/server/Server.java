package org.pupu.vertx_trials.server;

import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.pupu.vertx_trials.model.Database;
import org.pupu.vertx_trials.service.RouteGenHandler;

//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Random;
//import java.util.UUID;

public class Server extends AbstractVerticle {
//  private final String output = "Temperature recorded : ";
  private static final int httpPort = Integer.parseInt(System.getenv()
    .getOrDefault("HTTP_PORT", "8888"));
  private static final String dbUri = "mongodb://localhost:27017";
//  private final String uuid = UUID.randomUUID().toString();
//  private double temp = 21.0;
//  private final Random random = new Random();

  @Override
  public void start(Promise<Void> startPromise) {
//    vertx.setPeriodic(2000, this::updateTemperatureData);
    // Create Database object
    Database db = new Database();
    db.setDbUri(dbUri);
    // Create HTTP server
    HttpServer server = vertx.createHttpServer();
    // Create a router context handler using RouteGenHandler interface
    Router router = Router.router(vertx);
    RouteGenHandler.create(db, router);
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

  // Unused methods
//  // Method updates temperature randomly
//  private void updateTemperatureData(Long id) {
//    temp = temp+(random.nextGaussian() / 2.0d);
//    System.out.println(output+temp);
//
//    vertx.eventBus()
//      .publish("Temperature.updates", createPayload());
//  }
//
//  // Method to Record temperature Data
//  private void getTemperatureData(RoutingContext routingContext) {
//    System.out.println("Processing HTTP request from " +
//      routingContext.request().remoteAddress());
//    JsonObject payload = createPayload();
//    routingContext.response()
//      .putHeader("Content-type", "app/json")
//      .setStatusCode(200)
//      .end(payload.encodePrettily());
//  }
//
//  // Method to generate JSON payload with uuid, temperature and timestamp
//  private JsonObject createPayload() {
//    long millisec = System.currentTimeMillis();
//    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
//    Date date = new Date(millisec);
//    return new JsonObject()
//      .put("uuid", uuid)
//      .put("temperature", temp)
//      .put("timestamp", dateFormat.format(date));
//  }
}

