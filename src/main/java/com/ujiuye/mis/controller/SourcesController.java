package com.ujiuye.mis.controller;

import com.ujiuye.mis.pojo.Sources;
import com.ujiuye.mis.service.SourcesService;
import com.ujiuye.mis.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("sources")
public class SourcesController {
    @Autowired
    private SourcesService sourcesService;

    @GetMapping("list")
    /*@ResponseBody//将对象转成JSON格式返回给前端*/
    public R list() {

        Sources root = sourcesService.findRoot();

        //三元运算符
        return ObjectUtils.isEmpty(root) ? R.error().msg("查询失败") : R.ok().data("item", root);
    }


    @PostMapping("save")
    public R save(@RequestBody Sources sources) {
        boolean ret = sourcesService.save(sources);

        return ret ? R.ok() : R.error().msg("保存失败");

    }

    @GetMapping("all")
    public R findAll() {

        List<Sources> list = sourcesService.findAll();

        return CollectionUtils.isEmpty(list) ? R.error().msg("查询失败") : R.ok().data("items", list);
    }


    @GetMapping("{id}")
    public R findById(@PathVariable int id) {

        Sources sources = sourcesService.findById(id);
        return ObjectUtils.isEmpty(sources) ? R.error().msg("查询失败") : R.ok().data("item", sources);

    }

    @PostMapping("update")//修改后提交
    public R update(@RequestBody Sources sources) {
        boolean ret = sourcesService.update(sources);
        return ret ? R.ok() : R.error().msg("修改失败");
    }


    //删除
    @PostMapping("{id}")
    public R delete(@PathVariable int id) {
        boolean ret = sourcesService.delete(id);

        return ret ? R.ok() : R.error().msg("删除失败");


    }


    @GetMapping("own")
    public R own(HttpSession session) {
       /* TODO(当前用户ID)*/

        int eid = (Integer) session.getAttribute("active");
       List<Sources> list = sourcesService.findByEid(eid);

        return CollectionUtils.isEmpty(list) ? R.error().msg("查询失败") : R.ok().data("items",list);

    }

}
