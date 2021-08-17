package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Medications {

    public Medications() { }

    public Medications(final String medicationC) {
        this.medication = medicationC;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotBlank(message = "The medication cannot be null or empty.")
    private String medication;

    public Long getId() {
        return id;
    }

    public void setId(final Long idS) {
        this.id = idS;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(final String medicationS) {
        this.medication = medicationS;
    }

    @Override
    public String toString() {
        return "Medication{" + '\''
                + "id ='" + id + '\''
                + "medication ='" + medication + '\''
                + '}';
        }
    }
