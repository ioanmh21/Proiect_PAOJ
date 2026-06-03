package model;

public class Truck extends Vehicle {
    private double maxLoadCapacityTons;

    public Truck(String brand, String model, String plateNumber, double pricePerDay, double maxLoadCapacityTons) {
        super(brand, model, plateNumber, pricePerDay, LicenseCategory.C);
        this.maxLoadCapacityTons = maxLoadCapacityTons;
    }

    public double getMaxLoadCapacityTons() { return maxLoadCapacityTons; }
    public void setMaxLoadCapacityTons(double maxLoadCapacityTons) { this.maxLoadCapacityTons = maxLoadCapacityTons; }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.TRUCK;
    }
}
