package org.pupu.vertx_trials.server;

//import com.hazelcast.config.Config;
import io.vertx.core.Vertx;
import org.pupu.vertx_trials.service.ServerVerticle;
//import io.vertx.spi.cluster.hazelcast.ConfigUtil;

public class Main {

  public static void main(String[] args) {
     Vertx vertx = Vertx.vertx();
     vertx.deployVerticle(new ServerVerticle());
  }
}
