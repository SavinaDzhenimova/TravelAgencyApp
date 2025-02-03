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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.employeeRepository
                .findByUsername(username)
                .map(UserDetailsServiceImpl::map)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!"));
    }

    private static UserDetails map(Employee employee) {

        return new UserDetailsDTO(
                employee.getUsername(),
                employee.getPassword(),
                mapAuthorities(employee.getRole()),
                employee.getId(),
                employee.getFullName(),
                employee.getEmail(),
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
