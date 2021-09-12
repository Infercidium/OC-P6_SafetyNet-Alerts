package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Persons model stored in the database.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(
        columnNames = {"firstName", "lastName"})})
public class Persons {

    /**
     * Id attribute.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    /**
     * FirstName attribute.
     */
    @Column(name = "firstName")
    @NotBlank(message = "The first name cannot be null or empty.")
    private String firstName;

    /**
     * LastName attribute.
     */
    @Column(name = "lastName")
    @NotBlank(message = "The last name cannot be null or empty.")
    private String lastName;

    /**
     * Address attribute.
     */
    @ManyToOne
    @JoinColumn(name = "Address_id")
    private Address address;

    /**
     * City attribute.
     */
    @NotBlank(message = "The city cannot be null or empty.")
    private String city;

    /**
     * Zip attribute.
     */
    @Min(value = 1, message = "The zip must be a positive number.")
    private int zip;

    /**
     * Phone attribute.
     */
    private String phone;

    /**
     * Email attribute.
     */
    @Email(message = "A standard email address format is expected.")
    private String email;

    /**
     * Empty constructor creating an instance with no attribute value.
     */
    public Persons() { }

    /**
     * Constructor taking all the attributes not automatically generated,
     * instantiating all the attribute values.
     * @param firstNameC this is the firstName attribute.
     * @param lastNameC this is the lastName attribute.
     * @param addressC this is the address attribute.
     * @param cityC this is the city attribute.
     * @param zipC this is the zip attribute.
     * @param phoneC this is the phone attribute.
     * @param emailC this is the email attribute.
     */
    public Persons(final String firstNameC, final String lastNameC,
                   final Address addressC, final String cityC,
                   final int zipC, final String phoneC, final String emailC) {
        this.firstName = firstNameC;
        this.lastName = lastNameC;
        this.address = addressC;
        this.city = cityC;
        this.zip = zipC;
        this.phone = phoneC;
        this.email = emailC;
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
     * @return firstName attribute.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * FirstName setter.
     * @param firstNameS becomes the new firstName attribute.
     */
    public void setFirstName(final String firstNameS) {
        this.firstName = firstNameS;
    }

    /**
     * LastName getter.
     * @return lastName attribute.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * LastName setter.
     * @param lastNameS becomes the new lastName attribute.
     */
    public void setLastName(final String lastNameS) {
        this.lastName = lastNameS;
    }

    /**
     * Address getter.
     * @return address attribute.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Address setter.
     * @param addressS becomes the new address attribute.
     */
    public void setAddress(final Address addressS) {
        this.address = addressS;
    }

    /**
     * City getter.
     * @return city attribute.
     */
    public String getCity() {
        return city;
    }

    /**
     * City setter.
     * @param cityS becomes the new city attribute.
     */
    public void setCity(final String cityS) {
        this.city = cityS;
    }

    /**
     * Zip getter.
     * @return zip attribute.
     */
    public int getZip() {
        return zip;
    }

    /**
     * Zip setter.
     * @param zipS becomes the new zip attribute.
     */
    public void setZip(final int zipS) {
        this.zip = zipS;
    }

    /**
     * Phone getter.
     * @return phone attribute.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Phone setter.
     * @param phoneS becomes the new phone attribute.
     */
    public void setPhone(final String phoneS) {
        this.phone = phoneS;
    }

    /**
     * Email getter.
     * @return email attribute.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email setter.
     * @param emailS becomes the new email attribute.
     */
    public void setEmail(final String emailS) {
        this.email = emailS;
    }

    /**
     * ToString method allows you to see the content.
     * @return a String containing the name
     * of all the attributes and their contents.
     */
    @Override
    public String toString() {
        return "Persons{"
                + "id = " + id
                + ", firstName = '" + firstName + '\''
                + ", lastName = '" + lastName + '\''
                + ", address = '" + address + '\''
                + ", city = '" + city + '\''
                + ", zip = " + zip
                + ", phone = '" + phone + '\''
                + ", email = '" + email + '\''
                + '}';
    }
}
