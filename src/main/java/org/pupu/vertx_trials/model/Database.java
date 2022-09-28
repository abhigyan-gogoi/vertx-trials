package org.pupu.vertx_trials.model;

public class Database {

  private String dbUri;
  private String databaseName;
  private String collectionName;

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
}
