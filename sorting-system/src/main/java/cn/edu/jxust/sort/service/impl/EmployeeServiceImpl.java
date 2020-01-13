package cn.edu.jxust.sort.service.impl;

import cn.edu.jxust.sort.entity.po.Employee;
import cn.edu.jxust.sort.repository.EmployeeRepository;
import cn.edu.jxust.sort.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: ddh
 * @data: 2020/1/4 21:09
 * @description
 **/
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Integer deleteEmployeeByEnterpriseId(String enterpriseId) {
        return employeeRepository.deleteByEnterpriseId(enterpriseId);
    }

    @Override
    public Page<Employee> getAllEmployeeByEnterpriseId(String enterpriseId, Pageable pageable) {
        return employeeRepository.findAllByEnterpriseId(enterpriseId, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer deleteEmployeeByEmployeeCard(String employeeCard) {
        return employeeRepository.deleteByEmployeeCard(employeeCard);
    }

    @Override
    public Integer updateEmployeeByEmployeeCard(Employee employee) {
        return employeeRepository.updateByEmployeeCard(employee);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
