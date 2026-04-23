package model;

public abstract class Equipment implements Comparable<Equipment> {
    private String name;
    private double pricePerDay;
    private String condition; // good, deteriorated, in_repair
    private String serialNumber;
    private boolean available;

    public Equipment(String name, double pricePerDay, String condition, String serialNumber) {
        this.name = name;
        this.pricePerDay = pricePerDay;
        this.condition = condition;
        this.serialNumber = serialNumber;
        this.available = true;
    }

    @Override
    public int compareTo(Equipment other) {
        return Double.compare(this.pricePerDay, other.pricePerDay);
    }

    public abstract String getType();

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "[" + getType() + "] " + name + " | Serie: " + serialNumber +
               " | Pret/zi: " + pricePerDay + " RON | Stare: " + condition +
               " | Disponibil: " + (available ? "DA" : "NU");
    }
}
