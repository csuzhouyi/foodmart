package com.practice.foodmart.controller;


import com.practice.foodmart.pojo.Employee;
import com.practice.foodmart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: EmployeeController
 * @author: zhouyi
 * @create: 2019/3/26
 **/

@Controller
@RequestMapping(value = {"/employee"})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public ModelAndView screen(ModelAndView model, HttpServletRequest request){
        List<Employee> employees = new ArrayList<>();
        try {
            employees = employeeService.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.setViewName("employeeManage");
        model.addObject("employees", employees);
        return model;
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable int id) {
        try {
            employeeService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/employee";
    }

    /**
     *
     *
     */
    @GetMapping(value = "/create")
    public String postUser(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("action", "create");

        return "employeeForm";
    }

    /**
     *  创建
     *
     */
    @PostMapping(value = "/create")
    public String postUser(@ModelAttribute Employee employee) {
        try {
            employeeService.createEmployee(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/employee";
    }

//    @PostMapping(value = "/create")
//    public String postUser(Model model) {
//        model.addAttribute("employee", new Employee());
//        try {
//            employeeService.createEmployee(employee);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "redirect:/employee";
//    }

    /**
     *
     *
     *
     */
    @GetMapping(value = "/update/{id}")
    public String getUser(@PathVariable int id, Model model) {
        try {
            model.addAttribute("employee", employeeService.selectById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("action", "update");
        return "employeeForm";
    }


    /**
     *
     *
     */
    @PostMapping(value = "/update")
    public String putUser(@ModelAttribute Employee employee) {
        try {
            employeeService.update(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/employee";
    }




}
