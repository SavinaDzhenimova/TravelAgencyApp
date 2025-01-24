package org.travelagency.model.entity;

import jakarta.persistence.*;

import javax.validation.constraints.Positive;

@Entity
@Table(name = "days")
public class Day extends BaseEntity {

    @Column(nullable = false)
    @Positive
    private int dayNumber;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    private Program program;

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}