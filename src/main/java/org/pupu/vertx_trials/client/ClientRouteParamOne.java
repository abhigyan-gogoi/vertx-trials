package org.pupu.vertx_trials.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;

public class ClientRouteParamOne extends AbstractVerticle {
  private static final int httpPort = Integer.parseInt(System.getenv()
    .getOrDefault("HTTP_PORT", "8888"));
  @Override
  public void start(Promise<Void> startPromise) {
    // Create Vertx client instance
    WebClient client = WebClient.create(vertx);
    // Client config
    client
      .get(httpPort, "localhost", "/routes/ID-Name/")
      .expect(ResponsePredicate.SC_SUCCESS)
      .expect(ResponsePredicate.JSON)
      .send(res -> {
        // OK
        if (res.succeeded()){
          System.out.println("Got response from vertx server. Response: ");
          HttpResponse<Buffer> response = res.result();
          // Safely decode JSON response object
          JsonObject body = response.bodyAsJsonObject();
          // Print Json object
          System.out.println();
          System.out.println(body.toString());
          System.out.println();
          client.close();
        } else {
          System.out.println("Error: "+res.cause().getMessage());
        }

      });
  }
}
