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

    /**
     * Id attribute.
     */
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    /**
     * Medication attribute.
     */
    @NotBlank(message = "The medication cannot be null or empty.")
    @Column(unique = true)
    private String medication;

    /**
     * Empty constructor creating an instance with no attribute value.
     */
    public Medications() { }

    /**
     * Constructor taking all the attributes not automatically generated,
     * instantiating all the attribute values.
     * @param medicationC this is the medication attribute.
     */
    public Medications(final String medicationC) {
        this.medication = medicationC;
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
     * Medication getter.
     * @return medication attribute.
     */
    public String getMedication() {
        return medication;
    }

    /**
     * Medication setter.
     * @param medicationS becomes the new medication attribute.
     */
    public void setMedication(final String medicationS) {
        this.medication = medicationS;
    }

    /**
     * ToString method allows you to see the content.
     * @return a String containing the name
     * of all the attributes and their contents.
     */
    @Override
    public String toString() {
        return "Medication{"
                + " id = " + id
                + "medication = '" + medication + '\''
                + '}';
        }
    }
