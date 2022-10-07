package org.pupu.vertx_trials.model;

import io.vertx.core.json.JsonObject;

public interface Database {
  String getDbUri();

  void setDbUri(String dbUri);

  String getDatabaseName();

  void setDatabaseName(String databaseName);

  String getCollectionName();

  void setCollectionName(String collectionName);

  void setMongoConfig();

  JsonObject getDbConfig();
}
