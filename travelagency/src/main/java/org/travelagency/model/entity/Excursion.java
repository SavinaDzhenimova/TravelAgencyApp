package org.travelagency.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import org.travelagency.model.enums.TransportType;
import org.w3c.dom.stylesheets.LinkStyle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "excursions")
public class Excursion extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Positive
    private BigDecimal price;

    @Column(name = "dates")
    private List<LocalDate> dates;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransportType transportType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private Destination destination;

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Image> images;

    @OneToOne(optional = false)
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    private Program program;

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    @ManyToOne(optional = false)
    @JoinColumn(name = "guide_id", referencedColumnName = "id")
    private Employee guide;

    public Excursion() {
        this.images = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.dates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Employee getGuide() {
        return guide;
    }

    public void setGuide(Employee guide) {
        this.guide = guide;
    }
}