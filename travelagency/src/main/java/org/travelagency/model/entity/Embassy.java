package org.travelagency.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "embassies")
public class Embassy extends BaseEntity {

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private String fax;

    @Column(nullable = false, name = "duty_phone")
    private String dutyPhone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String webpage;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    public Embassy() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDutyPhone() {
        return dutyPhone;
    }

    public void setDutyPhone(String dutyPhone) {
        this.dutyPhone = dutyPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
