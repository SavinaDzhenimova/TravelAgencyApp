package org.travelagency.model.importDTO;

import java.util.ArrayList;
import java.util.List;

public class UpdateExcursionProgramDTO {

    private int endurance;

    private List<String> days;

    public UpdateExcursionProgramDTO() {
        this.days = new ArrayList<>();
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }
}
