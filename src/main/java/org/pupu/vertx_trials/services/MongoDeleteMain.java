package org.pupu.vertx_trials.services;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class MongoDeleteMain {

  public static void main(String[] args) {

    Vertx.clusteredVertx(new VertxOptions())
      .onSuccess(vertx -> {
        vertx.deployVerticle(new MongoDelete
          ("mongodb://localhost:27017", "people", "employees", "employee_id"));
        vertx.close();
      })
      .onFailure(failure -> System.out.println("ERROR: "+failure));
  }
}
