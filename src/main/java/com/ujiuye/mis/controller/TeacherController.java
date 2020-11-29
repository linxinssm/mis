package com.ujiuye.mis.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teach")
public class TeacherController {


    public  String  teach(){

        return "教师模块";
    }


}
