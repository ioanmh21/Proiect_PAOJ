package model;

import java.util.UUID;

public class Location {
    private UUID id;
    private String name;
    private String address;
    private String studioType;

    public Location(UUID id, String name, String address, String studioType) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.studioType = studioType;
    }

    public Location(String name, String address, String studioType) {
        this(UUID.randomUUID(), name, address, studioType);
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getStudioType() { return studioType; }
    public void setStudioType(String studioType) { this.studioType = studioType; }

    @Override
    public String toString() {
        return "Locatie: " + name + " | Adresa: " + address + " | Tip: " + studioType;
    }
}
