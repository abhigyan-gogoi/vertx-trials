package org.pupu.vertx_trials.service;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface RouteGenHandler extends Handler<RoutingContext> {
  void handle(RoutingContext routingContext);
}
