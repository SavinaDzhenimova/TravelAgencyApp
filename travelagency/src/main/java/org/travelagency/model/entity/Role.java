package org.travelagency.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.travelagency.model.enums.RoleName;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(min = 3, max = 300)
    private String description;

    public Role() {
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}