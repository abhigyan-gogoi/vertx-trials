package org.pupu.vertx_trials.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client extends AbstractVerticle {
  private static final int httpPort = Integer.parseInt(System.getenv()
    .getOrDefault("HTTP_PORT", "8888"));
  @Override
  public void start(Promise<Void> startPromise) {
    // Create Vertx client instance
    WebClient client = WebClient.create(vertx);
    // Method for client request
//    clientRequest(client);
    // Client config
    client
      .get(httpPort, "localhost", "/routes/exact-path")
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
          System.out.println(body.toString());
        } else {
          System.out.println("Error: "+res.cause().getMessage());
        }
      });
  }
//  private void clientRequest(WebClient client) {
//    System.out.print("REST call (GET): ");
//    // Enter data using BufferReader
//    BufferedReader reader = new BufferedReader(
//      new InputStreamReader(System.in));
//
//  }
}
