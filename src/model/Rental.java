package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Rental {
    private UUID id;
    private Client client;
    private Vehicle vehicle;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean returned;

    public Rental(UUID id, Client client, Vehicle vehicle, LocalDate startDate, LocalDate endDate) {
        this.id = id != null ? id : UUID.randomUUID();
        this.client = client;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = false;
    }

    public Rental(Client client, Vehicle vehicle, LocalDate startDate, LocalDate endDate) {
        this(UUID.randomUUID(), client, vehicle, startDate, endDate);
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public double calculateTotalPrice() {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if (days <= 0) days = 1;
        return days * vehicle.getPricePerDay();
    }

    public boolean isOverdue() {
        return !returned && LocalDate.now().isAfter(endDate);
    }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public boolean isReturned() { return returned; }
    public void setReturned(boolean returned) { this.returned = returned; }

    @Override
    public String toString() {
        return "Inchiriere: " + client.getName() + " -> " + vehicle.getBrand() + " " + vehicle.getModel() +
               " | " + startDate + " la " + endDate +
               " | Total: " + calculateTotalPrice() + " RON" +
               " | Returnat: " + (returned ? "DA" : "NU") +
               (isOverdue() ? " *** INTARZIAT ***" : "");
    }
}
