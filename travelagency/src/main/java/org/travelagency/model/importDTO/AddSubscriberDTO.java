package org.travelagency.model.importDTO;

import jakarta.validation.constraints.NotBlank;
import org.travelagency.model.annotations.ValidEmail;

public class AddSubscriberDTO {

    @NotBlank(message = "Трябва да въведете имейл!")
    @ValidEmail(message = "Имейлът трябва да бъде във формат example@domain.com")
    private String email;

    public AddSubscriberDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
