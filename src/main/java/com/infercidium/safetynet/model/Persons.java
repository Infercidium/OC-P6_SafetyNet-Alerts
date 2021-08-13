package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.jsoniter.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


@Entity
@JsonIgnoreProperties(value = "name")
public class Persons {


    public Persons() { this.name = new Name();}

    public Persons(final String firstname, final String lastname,
                   final String addressC, final String cityC,
                   final int zipC, final String phoneC, final String emailC) {
        this.name = new Name(firstname, lastname);
        this.address = addressC;
        this.city = cityC;
        this.zip = zipC;
        this.phone = phoneC;
        this.email = emailC;
    }

    @EmbeddedId
    @Column(unique = true)
    private Name name;

    @NotBlank(message = "The address cannot be null or empty.")
    private String address;

    @NotBlank(message = "The city cannot be null or empty.")
    private String city;

    @Min(value = 1, message = "The zip must be a positive number.")
    private int zip;

    private String phone;

    @Email(message = "A standard email address format is expected.")
    private String email;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getFirstName() {
        return name.getFirstName();
    }

    public void setFirstName(final String firstname) {
        this.name.setFirstName(firstname);
    }

    public String getLastName() {
        return name.getLastName();
    }

    public void setLastName(final String lastname) {
        this.name.setLastName(lastname);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String addressC) {
        this.address = addressC;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String cityC) {
        this.city = cityC;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(final int zipC) {
        this.zip = zipC;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phoneC) {
        this.phone = phoneC;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String emailC) {
        this.email = emailC;
    }

    @Override
    public String toString() {
        return "Persons{"
                + "firstName='" + name.getFirstName() + '\''
                + ", lastName='" + name.getLastName() + '\''
                + ", address='" + address + '\''
                + ", city='" + city + '\''
                + ", zip=" + zip + '\''
                + ", phone='" + phone + '\''
                + ", email='" + email + '\''
                + '}';
    }

}
