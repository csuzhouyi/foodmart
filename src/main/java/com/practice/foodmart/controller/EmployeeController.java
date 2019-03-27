package com.practice.foodmart.controller;


import com.practice.foodmart.pojo.Employee;
import com.practice.foodmart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    /**
     * 初始页面，展示所有员工信息
     * @param model
     * @param request
     * @return
     */
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

    /**
     * 删除员工
     * @param id
     * @return
     */
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
     * 创建新员工展示
     * @param model
     * @return
     */
    @GetMapping(value = "/create")
    public String createEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("action", "create");
        return "employeeForm";
    }

    /**
     * 创建新员工
     * @param employee
     * @return
     */
    @PostMapping(value = "/create")
    public String createEmployee(@ModelAttribute Employee employee) {
        try {
            employeeService.createEmployee(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/employee";
    }

    /**
     * 修改员工信息展示
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "/update/{id}")
    public String getEmployee(@PathVariable int id, Model model) {
        try {
            model.addAttribute("employee", employeeService.selectById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("action", "update");
        return "employeeForm";
    }

    /**
     * 修改员工信息
     * @param employee
     * @return
     */
    @PostMapping(value = "/update")
    public String updateEmployee(@ModelAttribute Employee employee) {
        try {
            employeeService.update(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/employee";
    }

}
