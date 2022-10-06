package org.pupu.vertx_trials.model;

import io.vertx.core.json.JsonObject;

public class EmployeeImpl implements Employee {
  private String _id;
  private String first_name;
  private String last_name;

  public EmployeeImpl() {

  }

  @Override
  public String getLast_name() {
    return last_name;
  }

  @Override
  public String getFirst_name() {
    return first_name;
  }

  @Override
  public String get_id() {
    return _id;
  }

  @Override
  public void set_id(String _id) {
    this._id = _id;
  }

  @Override
  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  @Override
  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  @Override
  public JsonObject getEmployeeJson() {
    return new JsonObject()
      .put("_id", this._id)
      .put("first_name", this.first_name)
      .put("last_name", this.last_name)
      ;
  }
}
