package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Medications {

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotBlank(message = "The medication cannot be null or empty.")
    @Column(unique = true)
    private String medication;

    public Medications() { }

    public Medications(final String medicationC) {
        this.medication = medicationC;
    }

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
        return "Medication{"
                + " id = " + id
                + "medication = '" + medication + '\''
                + '}';
        }
    }