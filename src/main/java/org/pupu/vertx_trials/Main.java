package org.pupu.vertx_trials;

import io.vertx.core.Vertx;
import org.pupu.vertx_trials.server.Server;

public class Main {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Server());
  }
}
