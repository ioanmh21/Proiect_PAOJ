import config.DatabaseConnection;
import model.*;
import service.BookingService;
import service.ClientService;
import service.VehicleService;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        DatabaseConnection.getInstance();

        VehicleService vehicleService = new VehicleService();
        ClientService clientService = new ClientService();
        BookingService bookingService = new BookingService();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== MENIU RENT-A-CAR ===");
            System.out.println("1. Adauga o masina (Car)");
            System.out.println("2. Inregistreaza un client");
            System.out.println("3. Inchiriaza un vehicul");
            System.out.println("4. Returneaza un vehicul");
            System.out.println("5. Afiseaza vehiculele disponibile");
            System.out.println("6. Cauta vehicule dupa categorie permis");
            System.out.println("7. Afiseaza inchirierile active");
            System.out.println("8. Afiseaza istoricul unui client");
            System.out.println("9. Calculeaza venitul total");
            System.out.println("10. Sterge un vehicul");
            System.out.println("11. Insereaza date de test (Seed)");
            System.out.println("0. Iesire");
            System.out.print("Alegeti o optiune: ");

            int option = -1;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Optiune invalida. Introduceti un numar.");
                continue;
            }

            switch (option) {
                case 1:
                    try {
                        System.out.print("Brand: ");
                        String brand = scanner.nextLine();
                        System.out.print("Model: ");
                        String model = scanner.nextLine();
                        System.out.print("Numar inmatriculare: ");
                        String plateNumber = scanner.nextLine();
                        System.out.print("Pret pe zi: ");
                        double price = Double.parseDouble(scanner.nextLine());
                        System.out.print("Numar locuri: ");
                        int seats = Integer.parseInt(scanner.nextLine());
                        
                        Car car = new Car(brand, model, plateNumber, price, seats);
                        vehicleService.addVehicle(car);
                    } catch (Exception e) {
                        System.out.println("Eroare la citirea datelor. Asigurati-va ca ati introdus valori corecte.");
                    }
                    break;
                case 2:
                    System.out.print("Nume: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Telefon: ");
                    String phone = scanner.nextLine();
                    System.out.print("CNP: ");
                    String cnp = scanner.nextLine();
                    
                    Client client = new Client(name, email, phone, cnp);
                    System.out.print("Categorii de permis (ex: B, C, CE separati prin virgula): ");
                    String[] cats = scanner.nextLine().split(",");
                    for (String cat : cats) {
                        if (!cat.trim().isEmpty()) {
                            try {
                                client.addLicenseCategory(LicenseCategory.valueOf(cat.trim().toUpperCase()));
                            } catch (IllegalArgumentException e) {
                                System.out.println("Categoria " + cat.trim() + " nu este valida si va fi ignorata.");
                            }
                        }
                    }
                    clientService.registerClient(client);
                    break;
                case 3:
                    System.out.print("CNP client: ");
                    String rentCnp = scanner.nextLine();
                    Client rentClient = clientService.getAllClients().stream()
                            .filter(c -> c.getCnp().equals(rentCnp))
                            .findFirst().orElse(null);
                    
                    if (rentClient == null) {
                        System.out.println("Clientul nu a fost gasit. Inregistrati-l mai intai.");
                        break;
                    }
                    
                    System.out.print("Numar inmatriculare vehicul: ");
                    String rentPlate = scanner.nextLine();
                    Vehicle rentVehicle = vehicleService.getAllVehicles().stream()
                            .filter(v -> v.getPlateNumber().equals(rentPlate))
                            .findFirst().orElse(null);
                            
                    if (rentVehicle == null) {
                        System.out.println("Vehiculul nu a fost gasit.");
                        break;
                    }
                    
                    try {
                        System.out.print("Numar de zile pentru inchiriere: ");
                        int days = Integer.parseInt(scanner.nextLine());
                        bookingService.rentVehicle(rentClient, rentVehicle, LocalDate.now(), LocalDate.now().plusDays(days));
                    } catch (NumberFormatException e) {
                        System.out.println("Numar de zile invalid.");
                    }
                    break;
                case 4:
                    System.out.print("CNP client: ");
                    String retCnp = scanner.nextLine();
                    Client retClient = clientService.getAllClients().stream()
                            .filter(c -> c.getCnp().equals(retCnp))
                            .findFirst().orElse(null);
                            
                    if (retClient == null) {
                        System.out.println("Clientul nu a fost gasit.");
                        break;
                    }
                    
                    System.out.print("Numar inmatriculare vehicul: ");
                    String retPlate = scanner.nextLine();
                    Vehicle retVehicle = vehicleService.getAllVehicles().stream()
                            .filter(v -> v.getPlateNumber().equals(retPlate))
                            .findFirst().orElse(null);
                            
                    if (retVehicle == null) {
                        System.out.println("Vehiculul nu a fost gasit.");
                        break;
                    }
                    
                    bookingService.returnVehicle(retClient, retVehicle);
                    break;
                case 5:
                    vehicleService.getAvailableVehicles();
                    break;
                case 6:
                    System.out.print("Categoria de permis (ex: B): ");
                    String categoryStr = scanner.nextLine();
                    try {
                        LicenseCategory category = LicenseCategory.valueOf(categoryStr.trim().toUpperCase());
                        vehicleService.searchVehiclesByCategory(category);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Categorie invalida.");
                    }
                    break;
                case 7:
                    bookingService.showActiveRentals();
                    break;
                case 8:
                    System.out.print("CNP client: ");
                    String histCnp = scanner.nextLine();
                    Client histClient = clientService.getAllClients().stream()
                            .filter(c -> c.getCnp().equals(histCnp))
                            .findFirst().orElse(null);
                            
                    if (histClient == null) {
                        System.out.println("Clientul nu a fost gasit.");
                    } else {
                        bookingService.showClientHistory(histClient);
                    }
                    break;
                case 9:
                    bookingService.calculateTotalRevenue();
                    break;
                case 10:
                    System.out.print("Numar inmatriculare vehicul de sters: ");
                    String delPlate = scanner.nextLine();
                    vehicleService.removeVehicle(delPlate);
                    break;
                case 11:
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
                    
                    try {
                        bookingService.rentVehicle(alice, bmw, LocalDate.now(), LocalDate.now().plusDays(3));
                        bookingService.rentVehicle(bob, dacia, LocalDate.now().minusDays(2), LocalDate.now().plusDays(5));
                    } catch (Exception e) {
                        System.out.println("Avertisment la popularea inchirierilor (posibil duplicate): " + e.getMessage());
                    }

                    try {
                        repository.LocationRepository.getInstance().create(new Location("Aeroport Otopeni", "Str. Aeroportului 1", "PREMIUM"));
                        repository.LocationRepository.getInstance().create(new Location("Gara de Nord", "Piata Garii de Nord", "STANDARD"));
                    } catch (Exception e) {
                        System.out.println("Avertisment la popularea locatiilor: " + e.getMessage());
                    }
                    
                    System.out.println("Date de test inserate cu succes (inclusiv Inchirieri si Locatii).");
                    break;
                case 0:
                    running = false;
                    System.out.println("La revedere!");
                    break;
                default:
                    System.out.println("Optiune invalida. Va rugam alegeti din meniu.");
            }
        }
        
        scanner.close();
    }
}
