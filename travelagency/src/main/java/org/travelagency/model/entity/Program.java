package org.travelagency.model.entity;

import jakarta.persistence.*;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "programs")
public class Program extends BaseEntity {

    @Column(nullable = false)
    @Positive
    private int endurance;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Day> days;

    @OneToOne(mappedBy = "program", cascade = CascadeType.ALL)
    private Excursion excursion;

    public Program() {
        this.days = new ArrayList<>();
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public Excursion getExcursion() {
        return excursion;
    }

    public void setExcursion(Excursion excursion) {
        this.excursion = excursion;
    }
}