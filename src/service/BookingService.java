package service;

import model.Client;
import model.Rental;
import model.Vehicle;
import repository.ClientRepository;
import repository.RentalRepository;
import repository.VehicleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingService {
    private RentalRepository rentalRepo = RentalRepository.getInstance();
    private VehicleRepository vehicleRepo = VehicleRepository.getInstance();
    private ClientRepository clientRepo = ClientRepository.getInstance();

    public boolean rentVehicle(Client client, Vehicle vehicle, LocalDate start, LocalDate end) {
        AuditService.getInstance().logAction("rentVehicle");
        
        if (!vehicle.isAvailable()) {
            System.out.println("Eroare: " + vehicle.getPlateNumber() + " indisponibil.");
            return false;
        }

        if (!client.hasLicenseCategory(vehicle.getRequiredLicenseCategory())) {
            System.out.println("Eroare: Permis invalid pentru " + vehicle.getBrand());
            return false;
        }

        List<Client> allClients = clientRepo.readAll();
        if (allClients.stream().noneMatch(c -> c.getCnp().equals(client.getCnp()))) {
            clientRepo.create(client);
        }

        Rental rental = new Rental(client, vehicle, start, end);
        rentalRepo.create(rental);
        
        vehicle.setAvailable(false);
        vehicleRepo.update(vehicle);

        System.out.println("Inchiriat: " + vehicle.getBrand() + " catre " + client.getName());
        return true;
    }

    public void returnVehicle(Client client, Vehicle vehicle) {
        AuditService.getInstance().logAction("returnVehicle");
        
        List<Rental> allRentals = rentalRepo.readAll(clientRepo.readAll(), vehicleRepo.readAll());
        Optional<Rental> activeRental = allRentals.stream()
                .filter(r -> r.getClient().getCnp().equals(client.getCnp()) 
                          && r.getVehicle().getPlateNumber().equals(vehicle.getPlateNumber()) 
                          && !r.isReturned())
                .findFirst();

        if (activeRental.isPresent()) {
            Rental rental = activeRental.get();
            rental.setReturned(true);
            rentalRepo.update(rental);
            
            Vehicle v = rental.getVehicle();
            v.setAvailable(true);
            vehicleRepo.update(v);
            
            double total = rental.calculateTotalPrice();
            System.out.println("Returnat: " + v.getPlateNumber() + " (" + total + " RON)");
        } else {
            System.out.println("Eroare: Nicio inchiriere activa gasita.");
        }
    }

    public void showActiveRentals() {
        AuditService.getInstance().logAction("showActiveRentals");
        System.out.println("\n--- Active ---");
        List<Rental> activeRentals = rentalRepo.readAll(clientRepo.readAll(), vehicleRepo.readAll()).stream()
                .filter(r -> !r.isReturned())
                .collect(Collectors.toList());
                
        if (activeRentals.isEmpty()) {
            System.out.println("Nu exista.");
        } else {
            activeRentals.forEach(System.out::println);
        }
    }

    public void showClientHistory(Client client) {
        AuditService.getInstance().logAction("showClientHistory");
        System.out.println("\n--- Istoric: " + client.getName() + " ---");
        List<Rental> history = rentalRepo.readAll(clientRepo.readAll(), vehicleRepo.readAll()).stream()
                .filter(r -> r.getClient().getCnp().equals(client.getCnp()))
                .collect(Collectors.toList());
                
        if (history.isEmpty()) {
            System.out.println("Niciun istoric.");
        } else {
            history.forEach(System.out::println);
        }
    }

    public void calculateTotalRevenue() {
        AuditService.getInstance().logAction("calculateTotalRevenue");
        double total = rentalRepo.readAll(clientRepo.readAll(), vehicleRepo.readAll()).stream()
                .filter(Rental::isReturned)
                .mapToDouble(Rental::calculateTotalPrice)
                .sum();
        System.out.println("Venit total: " + total + " RON");
    }
}
