package model;

public abstract class Vehicle implements Comparable<Vehicle> {
    private String brand;
    private String model;
    private String plateNumber;
    private double pricePerDay;
    private boolean available;
    private LicenseCategory requiredLicenseCategory;

    public Vehicle(String brand, String model, String plateNumber, double pricePerDay, LicenseCategory requiredLicenseCategory) {
        this.brand = brand;
        this.model = model;
        this.plateNumber = plateNumber;
        this.pricePerDay = pricePerDay;
        this.requiredLicenseCategory = requiredLicenseCategory;
        this.available = true;
    }

    @Override
    public int compareTo(Vehicle other) {
        return Double.compare(this.pricePerDay, other.pricePerDay);
    }

    public abstract VehicleType getVehicleType();

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public LicenseCategory getRequiredLicenseCategory() { return requiredLicenseCategory; }
    public void setRequiredLicenseCategory(LicenseCategory requiredLicenseCategory) { this.requiredLicenseCategory = requiredLicenseCategory; }

    @Override
    public String toString() {
        return "[" + getVehicleType() + "] " + brand + " " + model + " | Numar: " + plateNumber +
               " | Pret/zi: " + pricePerDay + " RON | Disponibil: " + (available ? "DA" : "NU") +
               " | Necesita Categoria: " + requiredLicenseCategory;
    }
}
