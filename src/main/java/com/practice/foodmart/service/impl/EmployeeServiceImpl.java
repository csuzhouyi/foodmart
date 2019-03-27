package com.practice.foodmart.service.impl;

import com.practice.foodmart.dao.EmployeeMapper;
import com.practice.foodmart.pojo.Employee;
import com.practice.foodmart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @description: EmployeeServiceImpl
 * @author: zhouyi
 * @create: 2019/3/26
 **/
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public List<Employee> selectAll() throws Exception {
        return employeeMapper.selectAll();
    }

    @Override
    public Employee selectById(int id) throws Exception {
        return employeeMapper.selectById(id);
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        return employeeMapper.deleteById(id);
    }

    @Override
    public boolean createEmployee(Employee employee) throws Exception {
        if (employee != null && selectById(employee.getEmployeeId()) != null)
            return false;

        employee.setFullName(employee.getFirstName() + " " + employee.getLastName());

        return employeeMapper.insertEmployee(employee);
    }

    @Override
    public boolean update(Employee employee) throws Exception {
        employee.setFullName(employee.getFirstName() + " " + employee.getLastName());
        return employeeMapper.update(employee);
    }
}
