package org.pupu.vertx_trials.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.pupu.vertx_trials.dao.MongoDao;
import org.pupu.vertx_trials.dao.MongoDaoImpl;
import org.pupu.vertx_trials.model.DatabaseConfig;

import java.util.List;

public class DatabaseServiceImpl implements DatabaseService{
  private final MongoDao mongoDao;

  public DatabaseServiceImpl() {
    this.mongoDao = new MongoDaoImpl();
  }

  @Override
  public Future<List<JsonObject>> showCollectionRecords(DatabaseConfig db, Vertx vertx) {
    return this.mongoDao.showCollectionRecords(db, vertx);
  }

  @Override
  public Future<List<String>> showCollections(DatabaseConfig db, Vertx vertx) {
    return this.mongoDao.showCollections(db, vertx);
  }

  @Override
  public Future<Void> insertCollection(DatabaseConfig db, Vertx vertx) {
    return this.mongoDao.insertCollection(db, vertx);
  }

  @Override
  public Future<Void> deleteCollection(DatabaseConfig db, Vertx vertx) {
    return this.mongoDao.deleteCollection(db, vertx);
  }
}
