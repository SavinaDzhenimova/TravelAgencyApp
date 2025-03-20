package org.travelagency.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "days")
public class Day extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    private Program program;

    public Day() {
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