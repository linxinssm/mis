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
        
         System.out.println("喜欢看你的笑容");
         System.out.println("喜欢洱海夕阳");
        return "林深时见鹿,海蓝时见鲸";
    }


}
