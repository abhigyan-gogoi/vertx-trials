package org.pupu.vertx_trials.service;

import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.pupu.vertx_trials.model.Database;

public interface RouteGenHandler extends Handler<RoutingContext> {
  static RouteGenHandler create(Database db, Router router){
    return new RouteGenHandlerImpl(db, router);
  }
}
