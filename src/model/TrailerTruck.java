package model;

public class TrailerTruck extends Vehicle {
    private double totalLengthMeters;

    public TrailerTruck(String brand, String model, String plateNumber, double pricePerDay, double totalLengthMeters) {
        super(brand, model, plateNumber, pricePerDay, LicenseCategory.CE);
        this.totalLengthMeters = totalLengthMeters;
    }

    public double getTotalLengthMeters() { return totalLengthMeters; }
    public void setTotalLengthMeters(double totalLengthMeters) { this.totalLengthMeters = totalLengthMeters; }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.TRAILER_TRUCK;
    }
}
