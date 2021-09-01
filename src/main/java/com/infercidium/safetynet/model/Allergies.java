package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;


@Entity
public class Allergies {

    /**
     * Id attribute.
     */
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    /**
     * Allergy attribute.
     */
    @NotBlank(message = "The allergie cannot be null or empty.")
    @Column(unique = true)
    private String allergy;

    /**
     * Empty constructor creating an instance with no attribute value.
     */
    public Allergies() { }

    /**
     * Constructor taking all the attributes not automatically generated,
     * instantiating all the attribute values.
     * @param allergyC this is the allergy attribute.
     */
    public Allergies(final String allergyC) {
        this.allergy = allergyC;
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
     * Allergy getter.
     * @return allergy attribute.
     */
    public String getAllergy() {
        return allergy;
    }

    /**
     * Allergy setter.
     * @param allergyS becomes the new allergy attribute.
     */
    public void setAllergy(final String allergyS) {
        this.allergy = allergyS;
    }

    /**
     * ToString method allows you to see the content.
     * @return a String containing the name
     * of all the attributes and their contents.
     */
    @Override
    public String toString() {
        return "Allergies{"
                + " id = " + id
                + ", allergy = '" + allergy + '\''
                + '}';
    }
}

