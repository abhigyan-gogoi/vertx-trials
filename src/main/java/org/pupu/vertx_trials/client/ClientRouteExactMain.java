package org.pupu.vertx_trials.client;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class ClientRouteExactMain {

  public static void main(String[] args) {

    Vertx.clusteredVertx(new VertxOptions())
      .onSuccess(vertx -> {
        vertx.deployVerticle(new ClientRouteExact());
        vertx.close();
      })
      .onFailure(failure -> System.out.println("ERROR: "+failure));
  }
}
