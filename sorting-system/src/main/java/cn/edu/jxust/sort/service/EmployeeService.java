package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author: ddh
 * @data: 2020/1/4 21:08
 * @description employee service
 **/
public interface EmployeeService {

    /**
     * 通过企业 id 删除员工
     *
     * @param enterpriseId 企业 id
     * @return Integer
     */
    Integer deleteEmployeeByEnterpriseId(String enterpriseId);

    /**
     * 分页获取企业所有员工
     *
     * @param enterpriseId 企业 id
     * @param pageable     分页信息
     * @return Page<Employee>
     */
    Page<Employee> getAllEmployeeByEnterpriseId(String enterpriseId, Pageable pageable);

    /**
     * 通过员工卡号删除员工信息
     *
     * @param employeeCard 员工卡号
     * @return Integer
     */
    Integer deleteEmployeeByEmployeeCard(String employeeCard);

    /**
     * 修改员工信息
     *
     * @param employee 员工实体
     * @return Integer
     */
    Integer updateEmployeeByEmployeeCard(Employee employee);

    /**
     * 创建员工
     *
     * @param employee 员工实体
     * @return Employee
     */
    Employee createEmployee(Employee employee);
}
