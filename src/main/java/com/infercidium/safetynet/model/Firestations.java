package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity
public class Firestations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Address_id", unique = true)
    private Address address;

    @Min(value = 1, message = "The station cannot be null or empty.")
    private int station;

    public Firestations() { }

    public Firestations(final Address addressC, final int stationC) {
        this.address = addressC;
        this.station = stationC;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long idS) {
        this.id = idS;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address addressS) {
        this.address = addressS;
    }

    public int getStation() {
        return station;
    }

    public void setStation(final int stationS) {
        this.station = stationS;
    }

    @Override
    public String toString() {
        return "Firestations{"
                + " id = " + id
                + ", address = '" + address + '\''
                + ", station = " + station
                + '}';
    }
}
