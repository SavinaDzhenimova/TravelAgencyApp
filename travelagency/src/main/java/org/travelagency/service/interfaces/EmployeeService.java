package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Employee;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.employee.EmployeesViewInfo;
import org.travelagency.model.user.EmployeeProfileDTO;

public interface EmployeeService {

    EmployeesViewInfo getAllEmployees();

    EmployeeProfileDTO getEmployeeInfo(Long id);

    void saveAndFlushEmployee(Employee employee);

    Result promoteEmployee(Long id);

    boolean deleteEmployeeById(Long id);

    Long getLoggedEmployeeId();
}
