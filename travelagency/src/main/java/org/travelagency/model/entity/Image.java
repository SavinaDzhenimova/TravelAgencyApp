package org.travelagency.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image extends BaseEntity {

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @ManyToOne(optional = false)
    @JoinColumn(name = "excursion_id", referencedColumnName = "id")
    private Excursion excursion;

    public Image() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Excursion getExcursion() {
        return excursion;
    }

    public void setExcursion(Excursion excursion) {
        this.excursion = excursion;
    }
}
