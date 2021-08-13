package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    private String medication;

    public Long getId() {
        return id;
    }

    public void setId(final Long idC) {
        this.id = idC;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(final String medicationC) {
        this.medication = medicationC;
    }

    @Override
    public String toString() {
        return "Medication{" + "id='" + id + '\''
                + "medication='" + medication + '\''
                + '}';
        }
    }
