package com.ujiuye.mis.service;

import com.ujiuye.mis.pojo.Dept;

import java.util.List;

/**
 * All rights Reserved, Designed By www.info4z.club
 * <p>title:com.ujiuye.mis.service</p>
 * <p>ClassName:DeptService</p>
 * <p>Description:TODO(请用一句话描述这个类的作用)</p>
 * <p>Compony:Info4z</p>
 * author:黄色闪光
 * date:2020/9/25
 * version:1.0
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface DeptService {

    List<Dept> findAll();

    List<Dept> findAllDept(int pageNum, int pageSize);

    boolean saveDept(Dept dept);

    boolean updateDept(Dept dept, int id);

    Dept findById(int id);

    boolean deleteDept(String ids);
}
