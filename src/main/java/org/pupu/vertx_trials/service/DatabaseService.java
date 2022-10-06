package org.pupu.vertx_trials.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.model.DatabaseConfig;

import java.util.List;

public interface DatabaseService {
  Future<Void> deleteCollection(DatabaseConfig db, Vertx vertx);
  Future<Void> insertCollection(DatabaseConfig db, Vertx vertx);
  Future<List<JsonObject>> showCollectionRecords(DatabaseConfig db, Vertx vertx);
  Future<List<String>> showCollections(DatabaseConfig db, Vertx vertx);
}
