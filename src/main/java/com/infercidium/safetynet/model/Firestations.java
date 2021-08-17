package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@JsonFilter("FirestationFilter")
public class Firestations {

    public Firestations() { }

    public Firestations(final String addressC, final int stationC) {
        this.address = addressC;
        this.station = stationC;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotBlank(message = "The address cannot be null or empty.")
    private String address;

    @NotNull(message = "The station cannot be null or empty.")
    private int station;

    public Long getId() {
        return id;
    }

    public void setId(final Long idS) {
        this.id = idS;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String addressS) {
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
        return "Firestations{" + '\''
                + "id =" + id + '\''
                + ", address ='" + address + '\''
                + ", station =" + station + '\''
                + '}';
    }
}
