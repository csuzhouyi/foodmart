package com.practice.foodmart.dao;

import com.practice.foodmart.pojo.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: EmployeeMapper
 * @author: zhouyi
 * @create: 2019/3/26
 **/
@Mapper
public interface EmployeeMapper {

    /**
     * 查询所有
     * */
    @Select("select * from employee")
    List<Employee> selectAll() throws Exception;

    @Select("select * from employee where employee_id = #{id}")
    Employee selectById(int id) throws Exception;

    @Delete("delete from employee where employee_id = #{id}")
    boolean deleteById(int id) throws Exception;

    @Insert("insert into employee(employee_id, full_name, first_name, last_name, store_id, " +
            "department_id, hire_date, end_date, salary, education_level, marital_status, birth_date, gender) " +
            "values(#{employeeId}, #{fullName}, #{firstName}, #{lastName}, #{storeId}, #{departmentId}, #{hireDate}, #{endDate}, #{salary}, " +
            "#{educationLevel}, #{maritalStatus}, #{birthDate}, #{gender})")
    boolean insertEmployee(Employee employee) throws Exception;

    @Update("update employee set full_name=#{fullName}, first_name=#{firstName}, last_name=#{lastName}, store_id=#{storeId}, departmentId=#{departmentId}, " +
            "hire_date=#{hireDate}, end_date=#{endDate}, salary=#{salary}, education_level=#{educationLevel}, marital_status=#{maritalStatus}, birth_date=#{birthDate}, " +
            "gender=#{gender},   where employee_id=#{employeeId}")
    boolean update(Employee employee) throws Exception;

}
