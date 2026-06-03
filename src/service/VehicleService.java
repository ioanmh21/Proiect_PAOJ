package service;

import model.LicenseCategory;
import model.Vehicle;
import repository.VehicleRepository;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleService {
    private VehicleRepository vehicleRepo = VehicleRepository.getInstance();

    public void addVehicle(Vehicle vehicle) {
        vehicleRepo.create(vehicle);
        AuditService.getInstance().logAction("addVehicle");
        System.out.println("Adaugat: " + vehicle.getBrand() + " " + vehicle.getModel());
    }

    public void removeVehicle(String plateNumber) {
        vehicleRepo.delete(plateNumber);
        AuditService.getInstance().logAction("removeVehicle");
        System.out.println("Sters: " + plateNumber);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.readAll();
    }

    public void getAvailableVehicles() {
        AuditService.getInstance().logAction("getAvailableVehicles");
        System.out.println("\n--- Disponibile ---");
        List<Vehicle> available = vehicleRepo.readAll().stream()
                .filter(Vehicle::isAvailable)
                .collect(Collectors.toList());
                
        if(available.isEmpty()) {
            System.out.println("Niciun vehicul disponibil.");
        } else {
            available.forEach(System.out::println);
        }
    }

    public void searchVehiclesByCategory(LicenseCategory category) {
        AuditService.getInstance().logAction("searchVehiclesByCategory");
        System.out.println("\n--- Categoria " + category + " ---");
        List<Vehicle> found = vehicleRepo.readAll().stream()
                .filter(v -> v.getRequiredLicenseCategory() == category)
                .collect(Collectors.toList());

        if (found.isEmpty()) {
            System.out.println("Nu s-au gasit.");
        } else {
            found.forEach(System.out::println);
        }
    }
}
