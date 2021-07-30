package com.infercidium.safetynet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Medications {

    public Medications() { }

    public Medications(String medication) { this.medication = medication; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medication;

    public Long getId() { return id;}

    public void setId(Long id) { this.id = id;}

    public String getMedication() {
            return medication;
        }

    public void setMedication(String medication) {
            this.medication = medication;
        }

    @Override
    public String toString() {
        return "Medication{" + "id='" + id + '\''
                + "medication='" + medication + '\''
                + '}';
        }
    }
