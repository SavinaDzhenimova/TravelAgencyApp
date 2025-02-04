package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Employee;
import org.travelagency.model.user.EmployeeProfileDTO;

public interface EmployeeService {

    EmployeeProfileDTO getEmployeeInfo(Long id);

    void saveAndFlushEmployee(Employee employee);
}
