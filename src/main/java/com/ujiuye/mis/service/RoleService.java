package com.ujiuye.mis.service;

import com.ujiuye.mis.pojo.Role;

import java.util.List;

/**
 * All rights Reserved, Designed By www.info4z.club
 * <p>title:com.ujiuye.mis.service</p>
 * <p>ClassName:RoleService</p>
 * <p>Description:TODO(请用一句话描述这个类的作用)</p>
 * <p>Compony:Info4z</p>
 * author:黄色闪光
 * date:2020/9/23
 * version:1.0
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface RoleService {


    List<Role> findAll();

    List<Role> page(int pageNum,int pageSize);

    boolean save(Role role, String ids);

    Role findById(int id);

    boolean update(Role role, String ids);

    boolean deleteRole(String ids);

}
