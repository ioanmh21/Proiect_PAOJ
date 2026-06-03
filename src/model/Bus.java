package model;

public class Bus extends Vehicle {
    private int passengerCapacity;

    public Bus(String brand, String model, String plateNumber, double pricePerDay, int passengerCapacity) {
        super(brand, model, plateNumber, pricePerDay, LicenseCategory.D);
        this.passengerCapacity = passengerCapacity;
    }

    public int getPassengerCapacity() { return passengerCapacity; }
    public void setPassengerCapacity(int passengerCapacity) { this.passengerCapacity = passengerCapacity; }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.BUS;
    }
}
