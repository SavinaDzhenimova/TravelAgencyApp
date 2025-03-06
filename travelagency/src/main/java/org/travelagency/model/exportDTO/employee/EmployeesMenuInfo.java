package org.travelagency.model.exportDTO.employee;

import java.util.List;

public class EmployeesMenuInfo {

    List<EmployeeDTO> employees;

    public EmployeesMenuInfo(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }
}
