package com.airtribe.meditrack.entity;

import java.io.Serializable;
import com.airtribe.meditrack.interfaces.Searchable;

/**
 * Abstract base class for Person entities (Doctor and Patient).
 */
public abstract class Person extends MedicalEntity implements Searchable, Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;
    protected String email;
    protected String phoneNumber;
    protected String address;

    public Person(String id, String name, String email, String phoneNumber, String address) {
        super(id);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
