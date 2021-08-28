package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotBlank(message = "Address cannot be null or empty.")
    @Column(unique = true)
    private String address;

    public Address() { }

    public Address(final String addressC) {
        this.address = addressC;
    }

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

    @Override
    public String toString() {
        return "Address{"
                + " id = " + id
                + ", address = '" + address + '\''
                + '}';
    }
}
