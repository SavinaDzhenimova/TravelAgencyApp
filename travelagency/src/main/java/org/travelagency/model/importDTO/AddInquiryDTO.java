package org.travelagency.model.importDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.travelagency.model.annotations.ValidEmail;

public class AddInquiryDTO {

    @NotBlank(message = "Името е задължително.")
    @Size(min = 5, max = 50, message = "Името трябва да бъде между 5 и 50 символа.")
    private String name;

    @NotBlank(message = "Имейлът е задължителен.")
    @ValidEmail(message = "Имейлът трябва да бъде във формат example@domain.com")
    private String email;

    @NotBlank(message = "Телефонният номер е задължителен.")
    @Size(min = 7, max = 15, message = "Телефонният номер трябва да съдържа между 7 и 15 символа!")
    private String phone;

    @NotBlank(message = "Запитването не може да бъде празно.")
    @Size(min = 5, message = "Запитването трябва да съдържа поне 5 символа.")
    private String message;

    public AddInquiryDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
