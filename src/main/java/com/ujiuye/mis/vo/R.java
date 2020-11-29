package com.ujiuye.mis.vo;

import com.ujiuye.mis.pojo.Sources;

import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.info4z.club
 * <p>title:com.ujiuye.mis.vo</p>
 * <p>ClassName:R</p>
 * <p>Description:TODO(请用一句话描述这个类的作用)</p>
 * <p>Compony:Info4z</p>
 * author:黄色闪光
 * date:2020/9/22
 * version:1.0
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
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
