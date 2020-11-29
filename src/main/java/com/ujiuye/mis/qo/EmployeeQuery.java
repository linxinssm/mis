package com.ujiuye.mis.qo;

//关键字查询封装类
public class EmployeeQuery {

    private  Integer condition;
    private String keyword;




    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }
}
