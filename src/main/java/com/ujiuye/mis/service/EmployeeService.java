package com.ujiuye.mis.service;

import com.ujiuye.mis.pojo.Employee;
import com.ujiuye.mis.qo.EmployeeQuery;

import java.util.List;

/**
 * All rights Reserved, Designed By www.info4z.club
 * <p>title:com.ujiuye.mis.service</p>
 * <p>ClassName:EmployeeService</p>
 * <p>Description:TODO(请用一句话描述这个类的作用)</p>
 * <p>Compony:Info4z</p>
 * author:黄色闪光
 * date:2020/9/24
 * version:1.0
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface EmployeeService {


    List<Employee> findByPageQuery(int pageNum, int pageSize, EmployeeQuery employeeQuery);

    boolean save(Employee employee, int roleid);

    Employee findById(Integer eid);

    boolean update(Employee employee, int roleid);

    boolean delete(String ids);

    List<Employee> findAll();

    Employee login(String username, String password);
}
