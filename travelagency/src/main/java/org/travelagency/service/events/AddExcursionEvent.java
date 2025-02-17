package org.travelagency.service.events;

import org.springframework.context.ApplicationEvent;

public class AddExcursionEvent extends ApplicationEvent {

    private String excursionName;

    private String email;

    public AddExcursionEvent(Object source, String excursionName, String email) {
        super(source);
        this.excursionName = excursionName;
        this.email = email;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
