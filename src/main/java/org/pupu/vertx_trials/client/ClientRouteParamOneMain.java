package org.pupu.vertx_trials.client;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class ClientRouteParamOneMain {

  public static void main(String[] args) {

    Vertx.clusteredVertx(new VertxOptions())
      .onSuccess(vertx -> {
        vertx.deployVerticle(new ClientRouteParamOne());
        vertx.close();
      })
      .onFailure(failure -> System.out.println("ERROR: "+failure));
  }
}
