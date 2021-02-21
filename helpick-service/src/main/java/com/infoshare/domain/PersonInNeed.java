package com.infoshare.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
@Entity
public class PersonInNeed {

    private String name;
    private String location;
    private String phone;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public PersonInNeed(String name, String location, String phone) {
        this.name = name;
        this.location = location;
        this.phone = phone;
    }

    public PersonInNeed() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonInNeed that = (PersonInNeed) o;
        return name.equals(that.name) && location.equals(that.location) && phone
            .equals(that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, phone);
    }

    @Override
    public String toString() {
        return "PersonInNeed{" + "name='" + name + '\'' + ", location='" + location + '\'' + '\'' + ", phone='" + phone
            + '\'' + '}';
    }
}
