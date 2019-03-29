package com.practice.foodmart.controller;

import com.github.pagehelper.PageInfo;
import com.practice.foodmart.pojo.AjaxResponseBody;
import com.practice.foodmart.pojo.Employee;
import com.practice.foodmart.pojo.SearchCriteria;
import com.practice.foodmart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ListUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 员工管理操作控制器
 * @author: zhouyi
 * @create: 2019/3/27
 **/

@RestController
@RequestMapping(value = "newEmployeeManage")
public class NewEmployeeManageController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "search")
    public ResponseEntity<?> searchByNameAjax(@Valid @RequestBody SearchCriteria searchCriteria, Errors errors){

        AjaxResponseBody result = new AjaxResponseBody();
        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }

        PageInfo<Employee> pageInfo = null;
        //List<Employee> employeeList = null;
        try {
            String idStr = searchCriteria.getEmployeeId();
            pageInfo = employeeService.search(idStr, searchCriteria.getPageIndex(), searchCriteria.getPageSize());
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("ERROR INFO: " + e);
            return ResponseEntity.ok(result);
        }
        if (ListUtils.isEmpty(pageInfo.getList())) {
            result.setMsg("no user found!");
        } else {
            result.setMsg("success");
        }
        result.setResult(pageInfo);
        return ResponseEntity.ok(result);

    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        AjaxResponseBody result = new AjaxResponseBody();
        try {
            if (employeeService.deleteById(id)) {
                result.setMsg("delete complete");
            } else {
                result.setMsg("delete fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("ERROR INFO: " + e);
        }
        return ResponseEntity.ok(result);
    }

}
