package com.infoshare.formobjects;

import com.infoshare.domain.TypeOfHelp;

import java.util.UUID;

public class VolunteerListObject {

    private String name;
    private String location;
    private String email;
    private String phone;
    private TypeOfHelp typeOfHelp;
    private boolean isAvailable;
    private final UUID uuid;

    public VolunteerListObject(String name, String location, String email, String phone, TypeOfHelp typeOfHelp, boolean isAvailable, UUID uuid) {
        this.name = name;
        this.location = location;
        this.email = email;
        this.phone = phone;
        this.typeOfHelp = typeOfHelp;
        this.isAvailable = isAvailable;
        this.uuid = uuid;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TypeOfHelp getTypeOfHelp() {
        return typeOfHelp;
    }

    public void setTypeOfHelp(TypeOfHelp typeOfHelp) {
        this.typeOfHelp = typeOfHelp;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public UUID getUuid() {
        return uuid;
    }


    public static final class VolunteerListObjectBuilder {
        private String name;
        private String location;
        private String email;
        private String phone;
        private TypeOfHelp typeOfHelp;
        private boolean isAvailable;
        private UUID uuid;

        private VolunteerListObjectBuilder() {
        }

        public static VolunteerListObjectBuilder aVolunteerListObject() {
            return new VolunteerListObjectBuilder();
        }

        public VolunteerListObjectBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public VolunteerListObjectBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        public VolunteerListObjectBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public VolunteerListObjectBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public VolunteerListObjectBuilder withTypeOfHelp(TypeOfHelp typeOfHelp) {
            this.typeOfHelp = typeOfHelp;
            return this;
        }

        public VolunteerListObjectBuilder withIsAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public VolunteerListObjectBuilder withUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public VolunteerListObject build() {
            return new VolunteerListObject(name, location, email, phone, typeOfHelp, isAvailable, uuid);
        }
    }
}
