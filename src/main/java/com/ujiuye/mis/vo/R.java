package com.ujiuye.mis.vo;

import com.ujiuye.mis.pojo.Sources;

import java.util.HashMap;
import java.util.Map;

//结果集的封装
public class R {

    private  Boolean status;//状态成功或者失败

    private  String msg;//失败后的提示信息

    private Map<String,Object> data =new HashMap<>();


    /*成功*/
    public  static  R ok(){

       return new R(){{
            setStatus(true);
            setMsg("操作成功");
        }};
    }

    /*失败*/
    public  static  R error(){

        return new R(){{
            setStatus(false);
        }};
    }

    /*写信息*/
    public  R msg(String msg) {
        this.msg=msg;

        return this;
    }

    /*数据*/
    public R data(Map<String,Object> map){
        this.data=map;

        return this;
    }

    public R data(String key,Object value){

        this.data.put(key,value);
        return this;
    }







    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
