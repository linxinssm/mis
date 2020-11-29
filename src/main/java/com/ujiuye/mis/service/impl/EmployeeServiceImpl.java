package com.ujiuye.mis.service.impl;

import com.github.pagehelper.PageHelper;
import com.ujiuye.mis.enunms.EmployeeQueryCondition;
import com.ujiuye.mis.mapper.DeptMapper;
import com.ujiuye.mis.mapper.EmpRoleMapper;
import com.ujiuye.mis.mapper.EmployeeMapper;
import com.ujiuye.mis.mapper.RoleMapper;
import com.ujiuye.mis.pojo.*;
import com.ujiuye.mis.qo.EmployeeQuery;
import com.ujiuye.mis.service.EmployeeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static  final Log LOG = LogFactory.getLog(EmployeeServiceImpl.class);


    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpRoleMapper empRoleMapper;

    @Override
    public List<Employee> findByPageQuery(int pageNum, int pageSize, EmployeeQuery employeeQuery) {

        //不同的情况加载不同的条件
        EmployeeExample employeeExample = new EmployeeExample();

        EmployeeExample.Criteria criteria = employeeExample.createCriteria();

        //根据员工姓名模糊查询
       /* if (employeeQuery.getCondition()== 1){//员工姓名
            criteria.andEnameLike("%"+employeeQuery.getKeyword()+"%");
        }*/

        //枚举的用法
        if (employeeQuery != null && employeeQuery.getCondition()== EmployeeQueryCondition.EMPLOYEE_EANME.getCondition()){//员工姓名
            criteria.andEnameLike("%"+employeeQuery.getKeyword()+"%");
        }

        //根据部门进行模糊查询,根据部门查询该部门中的员工
        if (employeeQuery != null && employeeQuery.getCondition()== EmployeeQueryCondition.DEPT_DNAME.getCondition()){//部门
            DeptExample deptExample = new DeptExample();
            deptExample.createCriteria().andDnameLike("%"+employeeQuery.getKeyword()+"%");
            List<Dept> depts = deptMapper.selectByExample(deptExample);
            List<Integer> deptIds = depts.stream().map(dept -> dept.getDeptno()).collect(Collectors.toList());

            criteria.andDfkIn(deptIds);//相当于select * from employee where dfk in (deptIds)3,4,5
        }

        PageHelper.startPage(pageNum,pageSize);
        List<Employee> employees = employeeMapper.selectByExample(employeeExample);

        employees.stream().forEach(emp->emp.setDept(deptMapper.selectByPrimaryKey(emp.getDfk())));

        return employees;
    }

    @Override
    @Transactional//添加员工
    public boolean save(Employee employee, int roleid) {

        try {
            employeeMapper.insertSelective(employee);

            System.out.println(employee.getEid());

            EmpRole empRole = new EmpRole();

            empRole.setRoleFk(roleid);
            empRole.setEmpFk(employee.getEid());
            empRole.setErdis(employee.getRemark());

            System.out.println(empRole);
            empRoleMapper.insertSelective(empRole);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("添加失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return false;
        }

    }

    @Override//修改页面信息回显
    public Employee findById(Integer eid) {
        Employee employee = employeeMapper.selectByPrimaryKey(eid);

        //根据传过来的eid是员工的id
        EmpRoleExample empRoleExample = new EmpRoleExample();
        empRoleExample.createCriteria().andEmpFkEqualTo(employee.getEid());

        //根据员工id查询中间表单中角色的id信息
        EmpRole empRole = empRoleMapper.selectByExample(empRoleExample).get(0);
        //根据角色id查询角色的信息
        Role role = roleMapper.selectByPrimaryKey(empRole.getRoleFk());

        //将角色的信息封装到员工中
        employee.setRole(role);

        //将员工信息返回
        return employee;
    }

    @Override
    @Transactional//员工修改
    public boolean update(Employee employee, int roleid) {
        try {
            employeeMapper.updateByPrimaryKeySelective(employee);

            EmpRoleExample empRoleExample = new EmpRoleExample();
            empRoleExample.createCriteria().andEmpFkEqualTo(employee.getEid());

            //先删除之前的信息
            //empRoleMapper.deleteByExample(empRoleExample);

            //在插入修改后的信息
            //创建中间表单的对象,将员工(id=employee.getEid())和角色(id=roleid)都存进去
            EmpRole empRole1 = empRoleMapper.selectByExample(empRoleExample).get(0);

            EmpRole empRole = new EmpRole();
            empRole.setErdis(employee.getRemark());
            empRole.setEmpFk(employee.getEid());
            empRole.setRoleFk(roleid);

            System.out.println(empRole1.getErid());
            empRole.setErid(empRole1.getErid());

            System.out.println(empRole);
            //  empRoleMapper.insertSelective(empRole);
            empRoleMapper.updateByPrimaryKeySelective(empRole);


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("修改失败");
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }


    }

    @Override
    @Transactional//事务管理,删除
    public boolean delete(String ids) {
        try {
            List<Integer> empIds = Arrays.stream(ids.split("-")).map(id -> Integer.valueOf(id)).collect(Collectors.toList());
            int a = 1/0;
            for (Integer empId : empIds) {
                EmpRoleExample empRoleExample =new EmpRoleExample();
                empRoleExample.createCriteria().andEmpFkEqualTo(empId);

                empRoleMapper.deleteByExample(empRoleExample);
                // 疑问
                /*   employeeMapper.deleteByPrimaryKey(empId);*/
            }

            empIds.stream().forEach(id->employeeMapper.deleteByPrimaryKey(id));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("清除失败");
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return false;
        }



    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = employeeMapper.selectByExample(null);

        employees.stream().forEach(e -> e.setDept(deptMapper.selectByPrimaryKey(e.getDfk())));

        return employees;
    }

    //登录
    @Override
    public Employee login(String username, String password) {

        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);

        List<Employee> employee = employeeMapper.selectByExample(employeeExample);//这里有索引越界异常
        if (CollectionUtils.isEmpty(employee)){
            return null;
        }

        return employee.get(0);

    }
}
