package com.ujiuye.mis.service.impl;

import com.github.pagehelper.PageHelper;
import com.ujiuye.mis.mapper.DeptMapper;
import com.ujiuye.mis.pojo.Dept;
import com.ujiuye.mis.pojo.DeptExample;
import com.ujiuye.mis.service.DeptService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeptServiceImpl implements DeptService {

    Log log= LogFactory.getLog(DeptServiceImpl.class);

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {

        return deptMapper.selectByExample(null);
    }

    @Override
    public List<Dept> findAllDept(int pageNum, int pageSize) {

        //返回的是一个Page<E>
        PageHelper.startPage(pageNum, pageSize);

        return deptMapper.selectByExample(null);
    }

    @Override
    public boolean saveDept(Dept dept) {
        int i = deptMapper.insertSelective(dept);
        System.out.println(i);

        return i>0;
    }

    @Override
    public boolean updateDept(Dept dept, int id) {
        DeptExample deptExample = new DeptExample();
        deptExample.createCriteria().andDeptnoEqualTo(id);

        return deptMapper.updateByExampleSelective(dept,deptExample)>0;
    }

    @Override
    public Dept findById(int id) {

        return  deptMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean deleteDept(String ids) {
        try {
            List<Integer> deptIds = Arrays.stream(ids.split("-")).map(id -> Integer.valueOf(id)).collect(Collectors.toList());

            for (Integer deptId : deptIds) {
                deptMapper.deleteByPrimaryKey(deptId);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除失败");
            return false;
        }

    }
}
