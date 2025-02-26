package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Employee;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.employee.EmployeesViewInfo;
import org.travelagency.model.importDTO.UpdatePasswordDTO;
import org.travelagency.model.user.EmployeeProfileDTO;

import java.util.Optional;

public interface EmployeeService {

    EmployeesViewInfo getAllEmployees();

    EmployeeProfileDTO getEmployeeInfo(Long id);

    void saveAndFlushEmployee(Employee employee);

    Result updateEmployeePassword(Long id, UpdatePasswordDTO updatePasswordDTO);

    Result promoteEmployee(Long id);

    boolean deleteEmployeeById(Long id);

    Optional<Employee> findEmployeeByEmail(String email);

    Optional<Employee> findEmployeeByPhoneNumber(String phoneNumber);

    Long getLoggedEmployeeId();

    Result updateEmployeeInfo(Long id, String infoToUpdate, String updatedInfo);
}