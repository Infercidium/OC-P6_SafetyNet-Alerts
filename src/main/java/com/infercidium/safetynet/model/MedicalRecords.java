package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

/**
 * MedicalRecords model stored in the database.
 */
@Entity
public class MedicalRecords {

    /**
     * Id attribute.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    /**
     * Birthdate attribute.
     */
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

    /**
     * Medications Set attribute.
     */
    @ManyToMany
    @JoinTable(name = "MedicalRecords_Medication",
    joinColumns = @JoinColumn(name = "MedicalRecords_id"),
    inverseJoinColumns = @JoinColumn(name = "Medications_id"))
    private Set<Medications> medications = new HashSet<>();

    /**
     * Allergies Set attribute.
     */
    @ManyToMany
    @JoinTable(name = "MedicalRecords_Allergies",
            joinColumns = @JoinColumn(name = "MedicalRecords_id"),
            inverseJoinColumns = @JoinColumn(name = "Allergies_id"))
    private Set<Allergies> allergies = new HashSet<>();

    /**
     * Persons attribute.
     */
    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private Persons persons;

    /**
     * Empty constructor creating an instance with no attribute value.
     */
    public MedicalRecords() { }

    /**
     * Constructor taking all the attributes not automatically generated,
     * instantiating all the attribute values.
     * @param birthdateC this is the birthdate attribute.
     * @param medicationsC this is the medications Set attribute.
     * @param allergiesC this is the allergies Set attribute.
     * @param personsC this is the persons attribute.
     */
    public MedicalRecords(final LocalDate birthdateC,
                          final Set<Medications> medicationsC,
                          final Set<Allergies> allergiesC,
                          final Persons personsC) {
        this.birthdate = birthdateC;
        this.medications = medicationsC;
        this.allergies = allergiesC;
        this.persons = personsC;
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
     * FirstName getter.
     * @return firstName attribute of Persons.
     */
    public String getFirstName() {
        return persons.getFirstName();
    }

    /**
     * Lastname getter.
     * @return lastName attribute of Persons.
     */
    public String getLastName() {
        return persons.getLastName();
    }

    /**
     * Birthdate getter.
     * @return birthdate attribute.
     */
    public LocalDate getBirthdate() {
        return birthdate;
    }

    /**
     * birthdate setter.
     * @param birthdateS becomes the new birthdate attribute.
     */
    public void setBirthdate(final LocalDate birthdateS) {
        this.birthdate = birthdateS;
    }

    /**
     * Age getter.
     * @return age of birthdate attribute.
     */
    public int getAge() {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    /**
     * Medications Set getter.
     * @return medications Set attribute.
     */
    public Set<Medications> getMedications() {
        return new HashSet<>(medications);
    }

    /**
     * Medications Set setter.
     * @param medicationsS becomes the new medications Set attribute.
     */
    public void setMedications(final Set<Medications> medicationsS) {
        this.medications = medicationsS;
    }

    /**
     * Allergies Set getter.
     * @return allergies Set attribute.
     */
    public Set<Allergies> getAllergies() {
        return new HashSet<>(allergies);
    }

    /**
     * Allergies Set setter.
     * @param allergiesS becomes the new allergies Set attribute.
     */
    public void setAllergies(final Set<Allergies> allergiesS) {
        this.allergies = allergiesS;
    }

    /**
     * Persons getter.
     * @return persons attribute.
     */
    public Persons getPersons() {
        Persons person;
        person = this.persons;
        return person;
    }

    /**
     * Persons setter.
     * @param personsS becomes the new persons attribute.
     */
    public void setPersons(final Persons personsS) {
        this.persons = personsS;
    }

    /**
     * Phone getter.
     * @return phone attribute.
     */
    public String getPhone() {
        return persons.getPhone();
    }

    /**
     * ToString method allows you to see the content.
     * @return a String containing the name
     * of all the attributes and their contents.
     */
    @Override
    public String toString() {
        return "MedicalRecords{"
                + " id = " + id
                + ", firstName = '" + persons.getFirstName() + '\''
                + ", lastName = '" + persons.getLastName() + '\''
                + ", birthdate = " + birthdate
                + ", medications = " + medications
                + ", allergies = " + allergies
                + ", person = " + persons.getId()
                + '}';
    }
}
