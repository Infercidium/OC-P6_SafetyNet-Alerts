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

    /**
     * Id attribute.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    /**
     * Address attribute.
     */
    @ManyToOne
    @JoinColumn(name = "Address_id", unique = true)
    private Address address;

    /**
     * Station attribute.
     */
    @Min(value = 1, message = "The station cannot be null or empty.")
    private int station;

    /**
     * Empty constructor creating an instance with no attribute value.
     */
    public Firestations() { }

    /**
     * Constructor taking all the attributes not automatically generated,
     * instantiating all the attribute values.
     * @param addressC this is the address attribute.
     * @param stationC this is the station attribute.
     */
    public Firestations(final Address addressC, final int stationC) {
        this.address = addressC;
        this.station = stationC;
    }

    /**
     * Id getter.
     * @return id attribute.
     */
    public Long getId() {
        return id;
    }

    /**
     * Id setter.
     * @param idS becomes the new id attribute.
     */
    public void setId(final Long idS) {
        this.id = idS;
    }

    /**
     * Address getter.
     * @return address attribute.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Address setter.
     * @param addressS becomes the new address attribute.
     */
    public void setAddress(final Address addressS) {
        this.address = addressS;
    }

    /**
     * Station getter.
     * @return station attribute.
     */
    public int getStation() {
        return station;
    }

    /**
     * Station setter.
     * @param stationS becomes the new station attribute.
     */
    public void setStation(final int stationS) {
        this.station = stationS;
    }

    /**
     * ToString method allows you to see the content.
     * @return a String containing the name
     * of all the attributes and their contents.
     */
    @Override
    public String toString() {
        return "Firestations{"
                + " id = " + id
                + ", address = '" + address + '\''
                + ", station = " + station
                + '}';
    }
}
