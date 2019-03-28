package com.practice.foodmart.service;

import com.github.pagehelper.PageInfo;
import com.practice.foodmart.pojo.Employee;

import java.util.List;

/**
 * @description: EmployeeService
 * @author: zhouyi
 * @create: 2019/3/26
 **/
public interface EmployeeService {

    List<Employee> selectAll() throws Exception;

    Employee selectById(int id) throws Exception;

    boolean deleteById(int id) throws Exception;

    boolean createEmployee(Employee employee) throws Exception;

    boolean update(Employee employee) throws Exception;

    PageInfo<Employee> search(String idStr, int pageIndex, int pageSize) throws Exception;
}
