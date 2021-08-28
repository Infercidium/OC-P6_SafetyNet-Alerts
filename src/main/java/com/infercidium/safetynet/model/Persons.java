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


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName", "lastName"})})
public class Persons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "firstName")
    @NotBlank(message = "The first name cannot be null or empty.")
    private String firstName;

    @Column(name = "lastName")
    @NotBlank(message = "The last name cannot be null or empty.")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "Address_id")
    private Address address;

    @NotBlank(message = "The city cannot be null or empty.")
    private String city;

    @Min(value = 1, message = "The zip must be a positive number.")
    private int zip;

    private String phone;

    @Email(message = "A standard email address format is expected.")
    private String email;

    public Persons() { }

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

    public Long getId() {
        return id;
    }

    public void setId(final Long idS) {
        this.id = idS;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstNameS) {
        this.firstName = firstNameS;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastNameS) {
        this.lastName = lastNameS;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address addressS) {
        this.address = addressS;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String cityS) {
        this.city = cityS;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(final int zipS) {
        this.zip = zipS;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phoneS) {
        this.phone = phoneS;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String emailS) {
        this.email = emailS;
    }

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
