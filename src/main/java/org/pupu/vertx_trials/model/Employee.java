package org.pupu.vertx_trials.model;

import io.vertx.core.json.JsonObject;

public class Employee {
  private String employee_id;
  private String first_name;
  private String last_name;

  public Employee() {
    this.generateEmployee();
  }

  public void generateEmployee(){
    this.employee_id = "ZL099";
    this.first_name = "Abhigyan";
    this.last_name = "Gogoi";
  }

  public JsonObject getEmployeeJson() {
    return new JsonObject()
      .put("Employee_ID", this.employee_id)
      .put("First_name", this.first_name)
      .put("Last_name", this.last_name)
      ;
  }
}
