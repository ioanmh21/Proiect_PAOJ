import model.*;
import service.RentalService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        RentalService service = new RentalService();

        // Date de test
        Camera canon = new Camera("Canon EOS R5", 150.0, "good", "CAM-001", 45, "Mirrorless");
        Camera nikon = new Camera("Nikon D850", 120.0, "good", "CAM-002", 36, "DSLR");
        Camera oldFilm = new Camera("Leica M6", 80.0, "deteriorated", "CAM-003", 0, "Film");

        Drone djiPro = new Drone("DJI Mavic 3 Pro", 200.0, "good", "DRN-001", 43, 15.0);
        Drone djiMini = new Drone("DJI Mini 4", 100.0, "good", "DRN-002", 34, 10.0);

        Laptop macbook = new Laptop("MacBook Pro M3", 180.0, "good", "LPT-001", 32, "Apple M3 Pro", "Final Cut, Lightroom, DaVinci");
        Laptop lenovo = new Laptop("Lenovo ThinkPad X1", 90.0, "good", "LPT-002", 16, "Intel i7", "Adobe Premiere, Photoshop");

        Projector epson = new Projector("Epson EB-L615U", 130.0, "good", "PRJ-001", 6000, "1080p", "Laser");
        Projector benq = new Projector("BenQ TK850", 110.0, "deteriorated", "PRJ-002", 3000, "4K", "DLP");

        // Adauga echipamente

        service.addEquipment(canon);
        service.addEquipment(nikon);
        service.addEquipment(oldFilm);
        service.addEquipment(djiPro);
        service.addEquipment(djiMini);
        service.addEquipment(macbook);
        service.addEquipment(lenovo);
        service.addEquipment(epson);
        service.addEquipment(benq);

        // Date de test

        Client alice = new Client("Alice Pop", "alice@gmail.com", "0721111111", "1990101123456");
        Client bob = new Client("Bob Ionescu", "bob@yahoo.com", "0732222222", "1985202234567");
        Client carol = new Client("Carol Marin", "carol@gmail.com", "0743333333", "1995303345678");

        // Inregistrare clienti

        service.registerClient(alice);
        service.registerClient(bob);
        service.registerClient(carol);


        // Inchirieri

        service.rentEquipment(alice, canon, LocalDate.now(), LocalDate.now().plusDays(3));
        service.rentEquipment(bob, djiPro, LocalDate.now(), LocalDate.now().plusDays(5));
        service.rentEquipment(carol, macbook, LocalDate.now(), LocalDate.now().plusDays(2));

        // Calcul pret estimat
        service.calculateRentalPrice(epson, LocalDate.now(), LocalDate.now().plusDays(4));

        // Echipamente disponibile

        service.getAvailableEquipment();

        // Cautare dupa tip

        service.searchByType("Drone");

        // Clienti cu inchirieri active

        service.showClientsWithActiveRentals();

        // Returnare echipament

        service.returnEquipment(alice, canon);

        // Istoricul unui client

        service.showClientHistory(alice);
        service.showClientHistory(bob);

        // Echipamente deteriorate

        service.getDeterioratedEquipment();
    }
}
