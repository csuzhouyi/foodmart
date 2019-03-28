package com.practice.foodmart.controller;

import com.practice.foodmart.pojo.Employee;
import com.practice.foodmart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        List<Employee> employeeList = new ArrayList<>();
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setFullName("test test");
        employee.setSalary(BigDecimal.valueOf(300));
        employee.setGender("M");
        employeeList.add(employee);
        model.addAttribute("employeeList", employeeList);
        return "/newEmployee/newEmployeeManage";
    }

}
