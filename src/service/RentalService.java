package service;

import model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RentalService {

    // TreeSet - echipamente sortate dupa pret/zi (Comparable implementat in Equipment)
    private TreeSet<Equipment> equipmentStock = new TreeSet<>();

    // HashMap - client -> lista inchirieri
    private HashMap<Client, List<Rental>> rentalHistory = new HashMap<>();

    // Lista inchirieri active
    private List<Rental> activeRentals = new ArrayList<>();

    // 1. Adauga echipament nou
    public void addEquipment(Equipment equipment) {
        equipmentStock.add(equipment);
        System.out.println("Echipament adaugat: " + equipment.getName());
    }


    // 2. Inregistreaza client nou
    public void registerClient(Client client) {
        if (!rentalHistory.containsKey(client)) {
            rentalHistory.put(client, new ArrayList<>());
            System.out.println("Client inregistrat: " + client.getName());
        } else {
            System.out.println("Clientul exista deja: " + client.getName());
        }
    }

    // 3. Inchiriaza echipament
    public boolean rentEquipment(Client client, Equipment equipment, LocalDate start, LocalDate end) {
        if (!equipment.isAvailable()) {
            System.out.println("Echipamentul " + equipment.getName() + " nu este disponibil!");
            return false;
        }
        if (!rentalHistory.containsKey(client)) {
            System.out.println("Clientul nu este inregistrat!");
            return false;
        }

        Rental rental = new Rental(client, equipment, start, end);
        equipment.setAvailable(false);
        activeRentals.add(rental);
        rentalHistory.get(client).add(rental);

        System.out.println("Inchiriere realizata: " + client.getName() + " -> " + equipment.getName());
        System.out.println("Total de plata: " + rental.calculateTotalPrice() + " RON");
        return true;
    }

    // 4. Returneaza echipament
    public void returnEquipment(Client client, Equipment equipment) {
        for (Rental rental : activeRentals) {
            if (rental.getClient().equals(client) &&
                rental.getEquipment().equals(equipment) &&
                !rental.isReturned()) {

                rental.setReturned(true);
                equipment.setAvailable(true);
                activeRentals.remove(rental);
                System.out.println("Echipament returnat: " + equipment.getName());
                return;
            }
        }
        System.out.println("Nu s-a gasit o inchiriere activa pentru acest echipament.");
    }

    // 5. Cauta echipamente dupa tip
    public List<Equipment> searchByType(String type) {
        List<Equipment> result = equipmentStock.stream()
                .filter(e -> e.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
        System.out.println("\nEchipamente de tip '" + type + "':");
        result.forEach(System.out::println);
        return result;
    }

    // 6. Afiseaza echipamentele disponibile
    public List<Equipment> getAvailableEquipment() {
        List<Equipment> result = equipmentStock.stream()
                .filter(Equipment::isAvailable)
                .collect(Collectors.toList());
        System.out.println("\nEchipamente disponibile (sortate dupa pret/zi):");
        result.forEach(System.out::println);
        return result;
    }

    // 7. Calculeaza pretul total al unei inchirieri
    public double calculateRentalPrice(Equipment equipment, LocalDate start, LocalDate end) {
        Rental temp = new Rental(null, equipment, start, end);
        double price = temp.calculateTotalPrice();
        System.out.println("Pret estimat pentru " + equipment.getName() + ": " + price + " RON");
        return price;
    }


    // 8. Afiseaza istoricul inchirierilor unui client
    public void showClientHistory(Client client) {
        System.out.println("\nIstoricul inchirierilor pentru: " + client.getName());
        List<Rental> history = rentalHistory.getOrDefault(client, new ArrayList<>());
        if (history.isEmpty()) {
            System.out.println("  Nicio inchiriere gasita.");
        } else {
            history.forEach(r -> System.out.println("  " + r));
        }
    }

    // 9. Afiseaza clientii cu inchirieri active
    public void showClientsWithActiveRentals() {
        System.out.println("\nClienti cu inchirieri active:");
        activeRentals.stream()
                .map(r -> r.getClient().getName() + " -> " + r.getEquipment().getName())
                .distinct()
                .forEach(s -> System.out.println("  " + s));
    }

    // 10. Afiseaza echipamentele deteriorate
    public List<Equipment> getDeterioratedEquipment() {
        List<Equipment> result = equipmentStock.stream()
                .filter(e -> e.getCondition().equalsIgnoreCase("deteriorated"))
                .collect(Collectors.toList());
        System.out.println("\nEchipamente deteriorate:");
        if (result.isEmpty()) System.out.println("  Niciun echipament deteriorat.");
        result.forEach(e -> System.out.println("  " + e));
        return result;
    }
}
