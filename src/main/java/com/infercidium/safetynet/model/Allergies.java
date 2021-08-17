package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;


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

    @NotBlank(message = "The allergie cannot be null or empty.")
    private String allergie;

    public Long getId() {
        return id;
    }

    public void setId(final Long idS) {
        this.id = idS;
    }

    public String getAllergie() {
        return allergie;
    }

    public void setAllergie(final String allergieS) {
        this.allergie = allergieS;
    }

    @Override
    public String toString() {
        return "Allergies{" + '\''
                + "id ='" + id + '\''
                + "allergie ='" + allergie + '\''
                + '}';
    }
}

