package org.travelagency.service.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.travelagency.service.interfaces.EmailService;

@Component
public class AddExcursionEventListener {

    private final EmailService emailService;

    public AddExcursionEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleAddExcursionEvent(AddExcursionEvent addExcursionEvent) {

        this.emailService.sendAddExcursionEmail(addExcursionEvent.getExcursionName(), addExcursionEvent.getEmail());
    }
}
