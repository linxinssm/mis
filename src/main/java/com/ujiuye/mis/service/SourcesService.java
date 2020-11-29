package com.ujiuye.mis.service;

import com.ujiuye.mis.pojo.Sources;

import java.util.List;

/**
 * All rights Reserved, Designed By www.info4z.club
 * <p>title:com.ujiuye.mis.service</p>
 * <p>ClassName:SourcesService</p>
 * <p>Description:TODO(请用一句话描述这个类的作用)</p>
 * <p>Compony:Info4z</p>
 * author:黄色闪光
 * date:2020/9/22
 * version:1.0
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface SourcesService {

    Sources findRoot();


    boolean save(Sources sources);

    List<Sources> findAll();

    Sources findById(int id);

    boolean update(Sources sources);

    boolean delete(int id);

    List<Sources> findByEid(int eid);
}
