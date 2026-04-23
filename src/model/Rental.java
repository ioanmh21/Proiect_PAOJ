package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rental {
    private Client client;
    private Equipment equipment;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean returned;

    public Rental(Client client, Equipment equipment, LocalDate startDate, LocalDate endDate) {
        this.client = client;
        this.equipment = equipment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = false;
    }

    public double calculateTotalPrice() {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if (days <= 0) days = 1;
        return days * equipment.getPricePerDay();
    }

    public boolean isOverdue() {
        return !returned && LocalDate.now().isAfter(endDate);
    }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Equipment getEquipment() { return equipment; }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public boolean isReturned() { return returned; }
    public void setReturned(boolean returned) { this.returned = returned; }

    @Override
    public String toString() {
        return "Inchiriere: " + client.getName() + " -> " + equipment.getName() +
               " | " + startDate + " - " + endDate +
               " | Total: " + calculateTotalPrice() + " RON" +
               " | Returnat: " + (returned ? "DA" : "NU") +
               (isOverdue() ? " *** INTARZIAT ***" : "");
    }
}
