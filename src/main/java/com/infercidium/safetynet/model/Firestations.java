package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    @ManyToMany
    @JoinTable(name = "Firestations_Address",
            joinColumns = @JoinColumn(name = "Firestations_id"),
            inverseJoinColumns = @JoinColumn(name = "Address_id"))
    private Set<Address> address;

    /**
     * Station attribute.
     */
    @Min(value = 1, message = "The station cannot be null or empty.")
    private int station;

    /**
     * Empty constructor creating an instance with no attribute value.
     */
    public Firestations() {
        this.address = new HashSet<>();
    }

    /**
     * Constructor taking all the attributes not automatically generated,
     * instantiating all the attribute values.
     *
     * @param addressC this is the address attribute.
     * @param stationC this is the station attribute.
     */
    public Firestations(final Set<Address> addressC, final int stationC) {
        this.address = addressC;
        this.station = stationC;
    }

    /**
     * Id getter.
     *
     * @return id attribute.
     */
    public Long getId() {
        return id;
    }

    /**
     * Id setter.
     *
     * @param idS becomes the new id attribute.
     */
    public void setId(final Long idS) {
        this.id = idS;
    }

    /**
     * Address getter.
     *
     * @return address attribute.
     */
    public Set<Address> getAddress() {
        return address;
    }

    /**
     * Address setter.
     *
     * @param addressS becomes the new address attribute.
     */
    public void setAddress(final Set<Address> addressS) {
        this.address = addressS;
    }

    /**
     * Station getter.
     *
     * @return station attribute.
     */
    public int getStation() {
        return station;
    }

    /**
     * Station setter.
     *
     * @param stationS becomes the new station attribute.
     */
    public void setStation(final int stationS) {
        this.station = stationS;
    }

    public void addAddress(final Address address) {
        if (!this.address.contains(address)) {
            this.address.add(address);
        }
    }

    /**
     * ToString method allows you to see the content.
     *
     * @return a String containing the name
     * of all the attributes and their contents.
     */
    @Override
    public String toString() {
        return "Firestations{"
                + "id = " + id
                + ", address = " + address
                + ", station = " + station
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Firestations)) return false;
        Firestations that = (Firestations) o;
        return getStation() == that.getStation() && getId().equals(that.getId()) && getAddress().equals(that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAddress(), getStation());
    }
}
