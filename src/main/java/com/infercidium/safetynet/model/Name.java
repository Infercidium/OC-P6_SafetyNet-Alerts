package com.infercidium.safetynet.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Name implements Serializable {

    public Name() { }

    public Name(String firstname, String lastname) {
        this.firstName = firstname;
        this.lastName = lastname;
    }

    @NotBlank(message = "The first name cannot be null or empty.")
    private String firstName;

    @NotBlank(message = "The last name cannot be null or empty.")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastname) {
        this.lastName = lastname;
    }

    @Override
    public String toString() {
        return "Name{" + '\''
                + "firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name name = (Name) o;
        return getFirstName().equals(name.getFirstName()) && getLastName().equals(name.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }
}
