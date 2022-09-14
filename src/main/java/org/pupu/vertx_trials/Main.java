package org.pupu.vertx_trials;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.server.Server;

public class Main {

  public static void main(String[] args) {
    // Vertx vertx = Vertx.vertx();
    // vertx.deployVerticle(new Server());
    // Run in multiple threads using setInstances(n)
    // n is number of thread instances
    //vertx.deployVerticle("org.pupu.vertx_trials.server.Server",
    //  new DeploymentOptions().setInstances(1));

    // eventBus() used for verticle communication
    // Eventing system, example usage below
    // NOTE: eventBus() implementation here is working
    // in main() process and NOT across the network
    //
    //vertx.eventBus()
    //  .<JsonObject>consumer("Temperature.updates", message -> {
    //    System.out.println(message.body().encodePrettily());
    //  });

    // Using clustered eventBus() allows us to have multiple
    // Instances of eventBus() working across the network
    // Code using clustered evenBus()
    Vertx.clusteredVertx(new VertxOptions())
      .onSuccess(vertx -> {
        vertx.deployVerticle(new Server());
      })
      .onFailure(failure -> {
        System.out.println("ERROR: "+failure);
      });
  }
}
