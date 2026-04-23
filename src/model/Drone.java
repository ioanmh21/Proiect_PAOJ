package model;

public class Drone extends Equipment {
    private int batteryAutonomyMinutes;
    private double maxDistanceKm;

    public Drone(String name, double pricePerDay, String condition, String serialNumber,
                 int batteryAutonomyMinutes, double maxDistanceKm) {
        super(name, pricePerDay, condition, serialNumber);
        this.batteryAutonomyMinutes = batteryAutonomyMinutes;
        this.maxDistanceKm = maxDistanceKm;
    }

    @Override
    public String getType() { return "Drone"; }

    public int getBatteryAutonomyMinutes() { return batteryAutonomyMinutes; }
    public void setBatteryAutonomyMinutes(int batteryAutonomyMinutes) { this.batteryAutonomyMinutes = batteryAutonomyMinutes; }

    public double getMaxDistanceKm() { return maxDistanceKm; }
    public void setMaxDistanceKm(double maxDistanceKm) { this.maxDistanceKm = maxDistanceKm; }

    @Override
    public String toString() {
        return super.toString() + " | Autonomie: " + batteryAutonomyMinutes + "min | Distanta max: " + maxDistanceKm + "km";
    }
}
