package org.travelagency.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Employee;
import org.travelagency.model.entity.Role;
import org.travelagency.model.user.UserDetailsDTO;
import org.travelagency.repository.EmployeeRepository;

import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public UserDetailsServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return this.employeeRepository
                .findByEmail(email)
                .map(UserDetailsServiceImpl::map)
                .orElseThrow(() -> new UsernameNotFoundException("Няма такъв потребител: " + email));
    }

    private static UserDetails map(Employee employee) {

        return new UserDetailsDTO(
                employee.getEmail(),
                employee.getPassword(),
                mapAuthorities(employee.getRole()),
                employee.getId(),
                employee.getFullName(),
                employee.getAddress(),
                employee.getPhoneNumber(),
                employee.getEducation(),
                employee.getSpecialty(),
                employee.getLanguages()
        );
    }

    private static Collection<? extends GrantedAuthority> mapAuthorities(Role role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
    }
}
