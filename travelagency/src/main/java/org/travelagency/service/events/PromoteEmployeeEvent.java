package org.travelagency.service.events;

import org.springframework.context.ApplicationEvent;

public class PromoteEmployeeEvent extends ApplicationEvent {

    private String fullName;

    private String email;

    public PromoteEmployeeEvent(Object source, String fullName, String email) {
        super(source);
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
