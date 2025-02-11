package org.travelagency.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import org.travelagency.model.enums.TransportType;
import org.w3c.dom.stylesheets.LinkStyle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "excursions")
public class Excursion extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Positive
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransportType transportType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private Destination destination;

    private List<String> imageUrl;

    @OneToOne(optional = false)
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    private Program program;

    public Excursion() {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}