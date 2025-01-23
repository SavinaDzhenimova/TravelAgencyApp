package org.travelagency.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "destinations")
public class Destination extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @Column(nullable = false, name = "visa_requirements")
    private String visaRequirements;

    @OneToOne
    @JoinColumn(name = "embassy_id", referencedColumnName = "id")
    private Embassy embassy;

    @Column(nullable = false, name = "time_to_visit")
    private String timeToVisit;

    @Column(nullable = false, name = "good_to_know")
    private String goodToKnow;
}