package com.infercidium.safetynet.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Firestations {

    public Firestations() { }

    public Firestations(final String addressC, final int stationC) {
        this.address = addressC;
        this.station = stationC;
    }

    @Id
    private String address;

    private int station;

    public String getAddress() {
        return address;
    }

    public void setAddress(final String addressC) {
        this.address = addressC;
    }

    public int getStation() {
        return station;
    }

    public void setStation(final int stationC) {
        this.station = stationC;
    }

    @Override
    public String toString() {
        return "Firestation{"
                + "address='" + address + '\''
                + ", station=" + station + '\''
                + '}';
    }
}
