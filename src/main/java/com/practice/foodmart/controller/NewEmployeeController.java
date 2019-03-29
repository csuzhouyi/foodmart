package com.practice.foodmart.controller;

import com.practice.foodmart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 新的员工管理初始页面控制器
 * @author: zhouyi
 * @create: 2019/3/27
 **/

@Controller
@RequestMapping(value = {"/newEmployee"})
public class NewEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public String screen(Model model) {
        return "/newEmployee/newEmployeeManage";
    }

}
