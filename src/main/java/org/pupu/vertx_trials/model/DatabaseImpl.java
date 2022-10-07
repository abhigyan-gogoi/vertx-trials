package org.pupu.vertx_trials.model;

import io.vertx.core.json.JsonObject;

public class DatabaseImpl implements Database{

  private String dbUri;
  private String databaseName;
  private String collectionName;

  private JsonObject dbConfig;

  @Override
  public String getDbUri() {
    return dbUri;
  }

  @Override
  public void setDbUri(String dbUri) {
    this.dbUri = dbUri;
  }

  @Override
  public String getDatabaseName() {
    return databaseName;
  }

  @Override
  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  @Override
  public String getCollectionName() {
    return collectionName;
  }

  @Override
  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }

  @Override
  public void setMongoConfig() {
    // Create JSON object for connecting to MongoDB server
    this.dbConfig = new JsonObject()
      .put("connection_uri", this.getDbUri())
      .put("db_name", this.getDatabaseName())
    ;
  }

  @Override
  public JsonObject getDbConfig() {
    return dbConfig;
  }
}
