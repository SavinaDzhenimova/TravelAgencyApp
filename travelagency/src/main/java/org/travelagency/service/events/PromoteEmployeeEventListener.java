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
    public void handleMakeOrderEvent(PromoteEmployeeEvent promoteEmployeeEvent) {

        this.emailService.promoteEmployeeEmail(promoteEmployeeEvent.getFullName(), promoteEmployeeEvent.getEmail());
    }
}
