package com.practice.foodmart.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.practice.foodmart.dao.EmployeeMapper;
import com.practice.foodmart.pojo.Employee;
import com.practice.foodmart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
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

    @Override
    public PageInfo<Employee> search(String idStr, @RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10")int pageSize) throws Exception{
        List<Employee> employeeList = null;
        PageInfo<Employee> pageInfo = new PageInfo<>();
        if (StringUtils.isEmpty(idStr)) {
            PageHelper.startPage(pageIndex, pageSize);
            employeeList = selectAll();

            int count = employeeMapper.count();

            pageInfo.setPageNum(pageIndex);
            pageInfo.setPageSize(pageSize);
            pageInfo.setTotal(count);
            pageInfo.setList(employeeList);
        } else {
            int id = Integer.valueOf(idStr);
            Employee employee = selectById(id);
            if (employee != null) {
                employeeList = new ArrayList<>();
                employeeList.add(employee);
                pageInfo.setPageNum(pageIndex);
                pageInfo.setPageSize(pageSize);
                pageInfo.setTotal(1);
                pageInfo.setList(employeeList);
            }
        }
        return pageInfo;
    }
}
