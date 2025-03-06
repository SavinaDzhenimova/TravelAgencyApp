package org.travelagency.service.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.travelagency.service.interfaces.EmailService;

@Component
public class AddCandidateEventListener {

    private final EmailService emailService;

    public AddCandidateEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleAddCandidateEvent(AddCandidateEvent addCandidateEvent) {

        this.emailService.sendAddCandidateEmail(addCandidateEvent.getFirstName(), addCandidateEvent.getLastName(),
                addCandidateEvent.getEmail(), addCandidateEvent.getPhoneNumber(), addCandidateEvent.getAddress(),
                addCandidateEvent.getEducation(), addCandidateEvent.getSpecialty(), addCandidateEvent.getLanguages(),
                addCandidateEvent.getDate());
    }
}
