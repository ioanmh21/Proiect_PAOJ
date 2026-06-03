package model;

public class Car extends Vehicle {
    private int numberOfSeats;

    public Car(String brand, String model, String plateNumber, double pricePerDay, int numberOfSeats) {
        super(brand, model, plateNumber, pricePerDay, LicenseCategory.B);
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.CAR;
    }
}
