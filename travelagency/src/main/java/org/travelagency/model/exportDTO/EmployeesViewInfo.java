package org.travelagency.model.exportDTO;

import java.util.List;

public class EmployeesViewInfo {

    List<EmployeeDTO> employees;

    public EmployeesViewInfo(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }
}