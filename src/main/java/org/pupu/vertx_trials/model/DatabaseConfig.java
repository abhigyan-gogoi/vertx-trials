package org.pupu.vertx_trials.model;

import io.vertx.core.json.JsonObject;

public class DatabaseConfig {

  private String dbUri;
  private String databaseName;
  private String collectionName;

  private JsonObject dbConfig;

  public String getDbUri() {
    return dbUri;
  }

  public void setDbUri(String dbUri) {
    this.dbUri = dbUri;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }

  public void setMongoConfig() {
    // Create JSON object for connecting to MongoDB server
    this.dbConfig = new JsonObject()
      .put("connection_uri", this.getDbUri())
      .put("db_name", this.getDatabaseName())
    ;
  }

  public JsonObject getDbConfig() {
    return dbConfig;
  }
}
