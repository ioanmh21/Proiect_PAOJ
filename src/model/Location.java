package model;

public class Location {
    private String name;
    private String address;
    private String studioType; // Studio foto, Outdoor, Sala evenimente

    public Location(String name, String address, String studioType) {
        this.name = name;
        this.address = address;
        this.studioType = studioType;
    }

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
