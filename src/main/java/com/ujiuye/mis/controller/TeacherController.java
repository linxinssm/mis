package com.ujiuye.mis.controller;


import com.ujiuye.mis.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teach")
public class TeacherController {


    @Autowired
    private DeptService deptService;

    @RequestMapping("/tea")
    public  String  teach(){

        System.out.println("哈哈哈");
        return "教师模块";
    }



}
