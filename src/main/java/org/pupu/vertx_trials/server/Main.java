package org.pupu.vertx_trials.server;

//import com.hazelcast.config.Config;
import io.vertx.core.Vertx;
import org.pupu.vertx_trials.service.ServerVerticle;
//import io.vertx.spi.cluster.hazelcast.ConfigUtil;

public class Main {

  public static void main(String[] args) {
     Vertx vertx = Vertx.vertx();
     vertx.deployVerticle(new ServerVerticle());
    // Run in multiple threads using setInstances(n)
    // n is number of thread instances
    //vertx.deployVerticle("org.pupu.vertx_trials.service.Server",
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

    // Programmatic Configuration of Hazelcast cluster
//    Config hazelcastConfig = ConfigUtil.loadConfig();
    // Set Cluster name
//    hazelcastConfig.setClusterName("my-cluster-name");
    // Disable hazelcast logging
//    hazelcastConfig.setProperty("hazelcast.logging.type", "none");
    // Set config to a cluster manager
    // ClusterManager mgr = new HazelcastClusterManager(hazelcastConfig);

    // Using clustered eventBus() allows us to have multiple
    // Instances of eventBus() working across the network
    // Code using clustered evenBus()
    // Setting a configured cluster manager
//    Vertx.clusteredVertx(new VertxOptions().setClusterManager(mgr))
//      .onSuccess(vertx -> vertx.deployVerticle(new Server()))
//      .onFailure(failure -> System.out.println("ERROR: "+failure));
//    Vertx.clusteredVertx(new VertxOptions())
//      .onSuccess(vertx -> vertx.deployVerticle(new Server()))
//      .onFailure(failure -> System.out.println("ERROR: "+failure));
  }
}
