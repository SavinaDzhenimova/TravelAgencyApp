package org.travelagency.service.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.travelagency.service.interfaces.EmailService;

@Component
public class ForgotPasswordEventListener {

    private final EmailService emailService;

    public ForgotPasswordEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleForgotPasswordEvent(ForgotPasswordEvent forgotPasswordEvent) {

        this.emailService.sendForgotPasswordEmail(forgotPasswordEvent.getFullName(), forgotPasswordEvent.getEmail(),
                forgotPasswordEvent.getPassword());
    }
}
