package org.travelagency.service.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.travelagency.service.interfaces.EmailService;

@Component
public class HireEmployeeEventListener {

    private final EmailService emailService;

    public HireEmployeeEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleHireEmployeeEvent(HireEmployeeEvent hireEmployeeEvent) {

        this.emailService.sendHireEmployeeEmail(hireEmployeeEvent.getFullName(), hireEmployeeEvent.getEmail(),
                hireEmployeeEvent.getPhoneNumber(), hireEmployeeEvent.getAddress(), hireEmployeeEvent.getEducation(),
                hireEmployeeEvent.getSpecialty(), hireEmployeeEvent.getLanguages());
    }
}
