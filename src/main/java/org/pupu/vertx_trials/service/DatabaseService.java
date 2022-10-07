package org.pupu.vertx_trials.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.model.Database;

import java.util.List;

public interface DatabaseService {
  Future<Void> deleteCollection(Database db, Vertx vertx);
  Future<Void> insertCollection(Database db, Vertx vertx);
  Future<List<JsonObject>> showCollectionRecords(Database db, Vertx vertx);
  Future<List<String>> showCollections(Database db, Vertx vertx);
}
