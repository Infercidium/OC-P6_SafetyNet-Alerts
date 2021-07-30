package com.infercidium.safetynet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Firestations {

    public Firestations() { }

    public Firestations(String address, int station) {
        this.address = address;
        this.station = station;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private int station;

    public Long getId() { return id;}

    public void setId(Long id) { this.id = id;}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "Firestation{"
                + "id='" + id + '\''
                + "address='" + address + '\''
                + ", station=" + station + '\''
                + '}';
    }
}
