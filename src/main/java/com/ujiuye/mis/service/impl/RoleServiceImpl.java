package com.ujiuye.mis.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ujiuye.mis.mapper.RoleMapper;
import com.ujiuye.mis.mapper.RoleSourcesMapper;
import com.ujiuye.mis.mapper.SourcesMapper;
import com.ujiuye.mis.pojo.*;
import com.ujiuye.mis.service.RoleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Log log= LogFactory.getLog(RoleServiceImpl.class);


    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleSourcesMapper roleSourcesMapper;

    @Autowired
    private SourcesMapper sourcesMapper;


    @Override//查询所有
    public List<Role> findAll() {

        return roleMapper.selectByExample(null);
    }

    @Override//分页查询,分页要有:当前页和每页的条数
    public List<Role> page(int pageNum, int pageSize) {

        //返回的是一个Page<E>
        PageHelper.startPage(pageNum, pageSize);

        return roleMapper.selectByExample(null);
    }

    @Override
    @Transactional//当操作有关联的表示要进行事务管理
    public boolean save(Role role, String ids) {

        try {
            /*insertSelective可以主键返回*/
            //将角色的数据先添加到角色的表中(role)
            roleMapper.insertSelective(role);
            //角色的信息添加完获取角色的ID
            Integer roleid = role.getRoleid();

            //这里的ids是每个角色对应的权限的ID(sources表即前端页面添加时选中的权限ID)
            List<Integer> sourceIds = Arrays.stream(ids.split("-")).map(id -> Integer.valueOf(id)).collect(Collectors.toList());

            for (Integer sourceId : sourceIds) {
           /* RoleSources roleSources = new RoleSources();
            //roleSources是中间表
            roleSources.setResourcesFk(sourceId);
            roleSources.setRoleFk(roleid);
            //roleid是角色ID添加时一个角色对应多个权限,因此此处roleid不变
            roleSourcesMapper.insertSelective(sourceId,roleid);
            */

                roleSourcesMapper.insertSelective(new RoleSources(sourceId, roleid));
            }

            //流式编程
            // sourceIds.stream().forEach(id->roleSourcesMapper.insertSelective(new RoleSources(id,roleid)));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("添加失败,手动回滚");

            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }


    }

    @Override//修改的数据回显
    public Role findById(int id) {
        //根据ID查询角色
        Role role = roleMapper.selectByPrimaryKey(id);

        //根据角色ID查询角色的权限,在中间表查询角色有的权限
        RoleSourcesExample roleSourcesExample = new RoleSourcesExample();
        roleSourcesExample.createCriteria().andRoleFkEqualTo(id);
        //返回Criteria原码:addCriterion("resources_fk =", id, "resourcesFk");
        //select * from sources where pid = id

        //一个角色对应的所有权限,[根据角色的id查询]
        List<RoleSources> roleSources = roleSourcesMapper.selectByExample(roleSourcesExample);

        System.out.println(roleSources);

        //提取一个角色对应所有的权限的Id
        List<Integer> sourceIds = roleSources.stream().map(rs -> rs.getResourcesFk()).collect(Collectors.toList());

        //查询权限表的所有信息
        List<Sources> sources = sourcesMapper.selectByExample(null);

        for (Sources source : sources) {
            if (sourceIds.contains(source.getId())){
                source.setChecked(true);
            }
        }
        //赋值给role
        role.setSourcesList(sources);


        return role;
    }

    @Override
    @Transactional
    public boolean update(Role role, String ids) {

        try {
            roleMapper.updateByPrimaryKey(role);

            //更新中间关联表,先删除中间表原有的信息在重新插入
            RoleSourcesExample roleSourcesExample = new RoleSourcesExample();
            //andRoleFkEqualTo(role.getRoleid())意思是添加RoleFk为条件=role.getRoleid
            roleSourcesExample.createCriteria().andRoleFkEqualTo(role.getRoleid());

            roleSourcesMapper.deleteByExample(roleSourcesExample);

            //在将roleid和ids成对插入中间表
            List<Integer> sourceIds = Arrays.stream(ids.split("-")).map(id -> Integer.valueOf(id)).collect(Collectors.toList());

            for (Integer sourceId : sourceIds) {
                roleSourcesMapper.insertSelective(new RoleSources(sourceId, role.getRoleid()));
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("更新失败,手动回滚");

            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional//删除
    public boolean deleteRole(String ids) {

        try {
            List<Integer> roleidList = Arrays.stream(ids.split("-")).map(id -> Integer.valueOf(id)).collect(Collectors.toList());

            for (Integer id : roleidList) {

                RoleSourcesExample roleSourcesExample = new RoleSourcesExample();
                roleSourcesExample.createCriteria().andRoleFkEqualTo(id);
                    //删除是一套角色表和中间表都要删除
                roleSourcesMapper.deleteByExample(roleSourcesExample);

                //外键没删除主键不能删除
                roleMapper.deleteByPrimaryKey(id);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("删除失败,手动回滚");
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }


    }


}
