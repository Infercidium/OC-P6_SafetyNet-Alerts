package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Address model stored in the database.
 */
@Entity
public class Address {

    /**
     * Id attribute.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    /**
     * Address attribute.
     */
    @NotBlank(message = "Address cannot be null or empty.")
    @Column(unique = true)
    private String address;

    /**
     * Empty constructor creating an instance with no attribute value.
     */
    public Address() { }

    /**
     * Constructor taking all the attributes not automatically generated,
     * instantiating all the attribute values.
     * @param addressC this is the address attribute.
     */
    public Address(final String addressC) {
        this.address = addressC;
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
     * Address getter.
     * @return address attribute.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Address setter.
     * @param addressS becomes the new address attribute.
     */
    public void setAddress(final String addressS) {
        this.address = addressS;
    }

    /**
     * ToString method allows you to see the content.
     * @return a String containing the name
     * of all the attributes and their contents.
     */
    @Override
    public String toString() {
        return "Address{"
                + " id = " + id
                + ", address = '" + address + '\''
                + '}';
    }

    /**
     * Equals methods.
     * @param o to test.
     * @return true if equals or false if not.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        Address address1 = (Address) o;
        return getAddress().equals(address1.getAddress())
                || getId().equals(address1.getId());
    }

    /**
     * HashCode methods.
     * @return hashed objects.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getAddress());
    }
}
