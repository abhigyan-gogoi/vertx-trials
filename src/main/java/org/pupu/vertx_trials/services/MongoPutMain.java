package org.pupu.vertx_trials.services;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class MongoPutMain {

  public static void main(String[] args) {

    Vertx.clusteredVertx(new VertxOptions())
      .onSuccess(vertx -> {
        vertx.deployVerticle(new MongoPut
          ("mongodb://localhost:27017", "people", "employees", "_id", "new_id"));
        vertx.close();
      })
      .onFailure(failure -> System.out.println("ERROR: "+failure));
  }
}
