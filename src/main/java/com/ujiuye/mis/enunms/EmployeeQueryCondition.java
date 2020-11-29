package com.ujiuye.mis.enunms;

public enum EmployeeQueryCondition {

    //枚举
   EMPLOYEE_EANME(1,"员工姓名"),
   DEPT_DNAME(2,"部门员工");

    EmployeeQueryCondition(Integer condition, String description) {
        this.condition = condition;
        this.description = description;
    }

    private Integer condition;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }


}
