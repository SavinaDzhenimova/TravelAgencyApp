package org.travelagency.model.importDTO;

import jakarta.validation.constraints.NotBlank;
import org.travelagency.model.annotations.ValidPassword;

public class UpdatePasswordDTO {

    @NotBlank(message = "Старата парола е задължителна!")
    private String oldPassword;

    @ValidPassword(message = "Паролата трябва да бъде между 8 и 20 символа и да съдържа поне една главна буква и поне една цифра!")
    private String newPassword;

    @NotBlank(message = "Потвърждението на паролата е задължително!")
    private String confirmNewPassword;

    public UpdatePasswordDTO() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
