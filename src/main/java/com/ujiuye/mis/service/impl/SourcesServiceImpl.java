package com.ujiuye.mis.service.impl;

import com.ujiuye.mis.mapper.EmpRoleMapper;
import com.ujiuye.mis.mapper.RoleSourcesMapper;
import com.ujiuye.mis.mapper.SourcesMapper;
import com.ujiuye.mis.pojo.*;
import com.ujiuye.mis.service.SourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SourcesServiceImpl implements SourcesService {

    @Autowired
    private SourcesMapper sourcesMapper;
    @Autowired
    private EmpRoleMapper empRoleMapper;
    @Autowired
    private RoleSourcesMapper roleSourcesMapper;

    @Override//保存
    public boolean save(Sources sources) {

        return sourcesMapper.insert(sources)>0;

    }

    //查询所有
    @Override
    public List<Sources> findAll() {

        return sourcesMapper.selectByExample(null);
    }

    @Override//根据id查询
    public Sources findById(int id) {

        return sourcesMapper.selectByPrimaryKey(id);
    }

    @Override//修改
    public boolean update(Sources sources) {

        return sourcesMapper.updateByPrimaryKey(sources)>0;
    }

    @Override//删除
    public boolean delete(int id) {

        return sourcesMapper.deleteByPrimaryKey(id)>0;
    }

    @Override//查询
    public List<Sources> findByEid(int eid) {
        // 查询当前用户对应的role的id
        EmpRoleExample empRoleExample = new EmpRoleExample();
        empRoleExample.createCriteria().andEmpFkEqualTo(eid);
        EmpRole empRole = empRoleMapper.selectByExample(empRoleExample).get(0);

        //根据role的id查询用户所拥有的权限的id
        RoleSourcesExample roleSourcesExample = new RoleSourcesExample();
        RoleSourcesExample.Criteria criteria = roleSourcesExample.createCriteria();
        criteria.andRoleFkEqualTo(empRole.getRoleFk());
        List<RoleSources> roleSources = roleSourcesMapper.selectByExample(roleSourcesExample);

        // 权限id
        List<Integer> sourceIds = roleSources.stream().map(rs -> rs.getResourcesFk()).collect(Collectors.toList());

        // 查找对应的所有的权限
        SourcesExample sourcesExample = new SourcesExample();
        sourcesExample.createCriteria().andIdIn(sourceIds);
        List<Sources> list = sourcesMapper.selectByExample(sourcesExample);

        // 筛选出顶级权限
//        Sources root = list.stream().filter(s -> s.getPid() == 0).collect(Collectors.toList()).get(0);
        Sources root = null;
        for (Sources sources : list) {
            if (sources.getPid()==0){
                root = sources;
            }
        }

        // 筛选出二级权限
//        List<Sources> son = list.stream().filter(s -> s.getPid() == root.getId()).collect(Collectors.toList());
        List<Sources> sons = new ArrayList<>();
        for (Sources sources : list) {
            if (sources.getPid()== root.getId()){
                sons.add(sources);
            }
        }
        //为二级菜单设置对应子菜单
            /*son.stream().forEach(s -> {
            s.setChildren(list.stream().filter(ss -> ss.getPid() == s.getId()).collect(Collectors.toList()));
        });*/

        for (Sources son : sons) {
            for (Sources sources : list) {
                if (sources.getPid()==son.getId()){
                    son.getChildren().add(sources);
                }
            }
        }

        return sons;
    }


    //查询根节点
    public Sources findRoot() {

        /*创建一个SourcesExample对象;
         * 查询root根节点的数据
         * */
        SourcesExample sourcesExample = new SourcesExample();

        /*sourcesExample.createCriteria()返回了一个Criteria*/
        /*Criteria继承了GeneratedCriteria(抽象,静态)类*/
        /*createCriteria().andPidEqualTo(0)<=等价=>(Criteria)GeneratedCriteria.andPidEqualTo(0)*/
        /*原码::[Criteria条件]
        *  public Criteria andPidEqualTo(Integer value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
            }
        */

        sourcesExample.createCriteria().andPidEqualTo(0);

        /*select * from sources where pid = 0
         *
         * sourcesMapper.selectByExample(sourcesExample)返回的是一个list集合
         *
         * 利用list.get(0)取出list集合中的元素
         * */

        Sources root = sourcesMapper.selectByExample(sourcesExample).get(0);


        //继续查询children的数据,是以root的ID为条件进行查询

        /*select * from sources where id = 1
         *
         * root.getId()是root的ID
         *
         * 递归方法:必须注意结束条件
         * */
        setChildren(root);

        return root;
    }

//提取公共代码
    private void setChildren(Sources root) {
        SourcesExample sourcesExample2 = new SourcesExample();
        sourcesExample2.createCriteria().andPidEqualTo(root.getId());//id=1

        //第二层目录
        List<Sources> sources = sourcesMapper.selectByExample(sourcesExample2);

        //判定条件不然会死循环下去知道到内存溢出抛出异常
        if (sources != null && sources.size() > 0) {
            //将第二级目录存到一级目录
            root.setChildren(sources);
            //第三层目录
            for (Sources source : sources) {
                setChildren(source);
            }

        }
    }
}
