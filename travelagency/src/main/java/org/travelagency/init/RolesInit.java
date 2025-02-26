package org.travelagency.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.Employee;
import org.travelagency.model.entity.Language;
import org.travelagency.model.entity.Role;
import org.travelagency.model.enums.EducationLevel;
import org.travelagency.model.enums.RoleName;
import org.travelagency.repository.EmployeeRepository;
import org.travelagency.repository.LanguageRepository;
import org.travelagency.repository.RoleRepository;

import java.util.*;

@Component
@Order(6)
public class RolesInit implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final LanguageRepository languageRepository;
    private final PasswordEncoder passwordEncoder;

    public RolesInit(EmployeeRepository employeeRepository, RoleRepository roleRepository,
                     LanguageRepository languageRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.languageRepository = languageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (this.roleRepository.count() == 0) {

            Arrays.stream(RoleName.values())
                    .forEach(roleName -> {
                        Role role = new Role();
                        role.setRoleName(roleName);

                        String name = switch (roleName) {
                            case MANAGER -> "Мениджър";
                            case EMPLOYEE -> "Служител";
                        };

                        String description = switch (roleName) {
                            case MANAGER -> "Мениджърът може да разглежда кандидатури, да ги одобрява, да добавя дестинации и екскурзии и има достъп до информация за всички работници като може да ги повишава в мениджъри.";
                            case EMPLOYEE -> "Работникът може да вижда информация за себе си в своя профил както и информация за всички дестинации, екскурзии и своите колеги.";
                        };

                        role.setName(name);
                        role.setDescription(description);
                        this.roleRepository.saveAndFlush(role);
                    });
        }

        if (this.employeeRepository.count() == 0) {

            Employee employee = new Employee();
            Optional<Role> optionalRole = this.roleRepository.findByRoleName(RoleName.MANAGER);
            Optional<Language> optionalLanguage = this.languageRepository.findByNameIgnoreCase("английски");

            optionalRole.ifPresent(employee::setRole);
            optionalLanguage.ifPresent(language -> employee.getLanguages().add(language));

            employee.setFullName("Sunrise Travel Мениджър");
            employee.setPhoneNumber("0888232555");
            employee.setEmail("sunrisetravelagencybulgaria@gmail.com");
            employee.setAddress("бул.Съединение №83, 4600 Велинград, България");
            employee.setPassword(this.passwordEncoder.encode("Admin1234"));
            employee.setEducation(EducationLevel.UNIVERSITY_DEGREE);
            employee.setSpecialty("Хардуерни и софтуерни системи");

            this.employeeRepository.saveAndFlush(employee);
        }
    }
}
