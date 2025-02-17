package org.travelagency.service.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.travelagency.service.interfaces.EmailService;

@Component
public class PromoteEmployeeEventListener {

    private final EmailService emailService;

    public PromoteEmployeeEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handlePromoteEmployeeEvent(PromoteEmployeeEvent promoteEmployeeEvent) {

        this.emailService.sendPromoteEmployeeEmail(promoteEmployeeEvent.getFullName(), promoteEmployeeEvent.getEmail());
    }
}
