package com.ujiuye.mis.controller;

import com.github.pagehelper.PageInfo;
import com.ujiuye.mis.pojo.Dept;
import com.ujiuye.mis.service.DeptService;
import com.ujiuye.mis.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("all")
    public R findAll(){
        List<Dept> list = deptService.findAll();

        return CollectionUtils.isEmpty(list) ? R.error().msg("查询失败") : R.ok().data("items",list);
    }

    @GetMapping("all/{pageNum}/{pageSize}")
    public R findAllDept(@PathVariable int pageNum,@PathVariable int pageSize){
        List<Dept> list = deptService.findAllDept(pageNum,pageSize);

        return CollectionUtils.isEmpty(list) ? R.error().msg("查询失败") : R.ok().data("items",new PageInfo<>(list));
    }


    @PostMapping("save")
    public R saveDept(@RequestBody Dept dept){
        boolean ret = deptService.saveDept(dept);

        return ret ? R.ok() : R.error().msg("添加失败");
    }

    @PostMapping("update/{id}")
    public R updateDept(@RequestBody Dept dept,@PathVariable int id){
        boolean ret = deptService.updateDept(dept,id);

        return ret ? R.ok() : R.error().msg("添加失败");
    }

    @GetMapping("{id}")
    public R findDept(@PathVariable int id){
        Dept dept = deptService.findById(id);

        return ObjectUtils.isEmpty(dept) ? R.error().msg("添加失败") :  R.ok().data("item",dept) ;
    }


    @GetMapping("del/{ids}")
    public R deleteDept(@PathVariable String ids){
        boolean ret = deptService.deleteDept(ids);

        return ret ? R.ok() : R.error().msg("删除失败");
    }


}
