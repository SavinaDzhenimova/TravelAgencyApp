package org.travelagency.service.interfaces;

import org.travelagency.model.user.EmployeeProfileDTO;

public interface EmployeeService {

    EmployeeProfileDTO getEmployeeInfo(Long id);
}
