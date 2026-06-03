import config.DatabaseConnection;
import model.*;
import service.BookingService;
import service.ClientService;
import service.VehicleService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        
        // Initializare conexiune baza de date (creeaza si tabelele)
        DatabaseConnection.getInstance();

        VehicleService vehicleService = new VehicleService();
        ClientService clientService = new ClientService();
        BookingService bookingService = new BookingService();

        Car bmw = new Car("BMW", "Series 3", "B-123-ABC", 250.0, 5);
        Car dacia = new Car("Dacia", "Logan", "B-456-DEF", 100.0, 5);
        Motorcycle yamaha = new Motorcycle("Yamaha", "MT-07", "CJ-99-MTO", 150.0, LicenseCategory.A2, false);
        Truck volvo = new Truck("Volvo", "FH16", "IS-10-TRK", 500.0, 20.0);
        TrailerTruck scania = new TrailerTruck("Scania", "R500", "B-88-TIR", 800.0, 18.5);
        Bus mercedes = new Bus("Mercedes", "Tourismo", "CT-22-BUS", 1200.0, 50);

        vehicleService.addVehicle(bmw);
        vehicleService.addVehicle(dacia);
        vehicleService.addVehicle(yamaha);
        vehicleService.addVehicle(volvo);
        vehicleService.addVehicle(scania);
        vehicleService.addVehicle(mercedes);

        Client alice = new Client("Alice Pop", "alice@gmail.com", "0721111111", "2901010123456");
        alice.addLicenseCategory(LicenseCategory.B);

        Client bob = new Client("Bob Ionescu", "bob@yahoo.com", "0732222222", "1852020234567");
        bob.addLicenseCategory(LicenseCategory.B);
        bob.addLicenseCategory(LicenseCategory.C);
        bob.addLicenseCategory(LicenseCategory.CE);

        Client carol = new Client("Carol Marin", "carol@gmail.com", "0743333333", "2953030345678");
        carol.addLicenseCategory(LicenseCategory.A2);
        carol.addLicenseCategory(LicenseCategory.B);

        clientService.registerClient(alice);
        clientService.registerClient(bob);
        clientService.registerClient(carol);

        bookingService.rentVehicle(alice, bmw, LocalDate.now(), LocalDate.now().plusDays(3));
        bookingService.rentVehicle(alice, volvo, LocalDate.now(), LocalDate.now().plusDays(2));
        bookingService.rentVehicle(bob, scania, LocalDate.now(), LocalDate.now().plusDays(5));
        bookingService.rentVehicle(carol, yamaha, LocalDate.now(), LocalDate.now().plusDays(2));

        vehicleService.getAvailableVehicles();
        vehicleService.searchVehiclesByCategory(LicenseCategory.B);
        vehicleService.searchVehiclesByCategory(LicenseCategory.D);

        bookingService.showActiveRentals();
        bookingService.returnVehicle(alice, bmw);
        
        bookingService.showClientHistory(alice);
        bookingService.showClientHistory(bob);

        bookingService.calculateTotalRevenue();

        vehicleService.removeVehicle("B-456-DEF");

        vehicleService.getAvailableVehicles();
    }
}
