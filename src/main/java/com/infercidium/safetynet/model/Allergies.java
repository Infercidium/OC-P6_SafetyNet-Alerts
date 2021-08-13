package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Allergies {

    public Allergies() { }

    public Allergies(final String allergieC) {
        this.allergie = allergieC;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String allergie;

    public Long getId() {
        return id;
    }

    public void setId(final Long idC) {
        this.id = idC;
    }

    public String getAllergie() {
        return allergie;
    }

    public void setAllergie(final String allergieC) {
        this.allergie = allergieC;
    }

    @Override
    public String toString() {
        return "Allergies{"
                + "id='" + id + '\''
                + "allergie ='" + allergie + '\''
                + '}';
    }
}

