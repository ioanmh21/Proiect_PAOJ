package repository;

import config.DatabaseConnection;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RentalRepository {
    private static RentalRepository instance;
    private Connection connection;

    private RentalRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static synchronized RentalRepository getInstance() {
        if (instance == null) {
            instance = new RentalRepository();
        }
        return instance;
    }

    public void create(Rental rental) {
        String sql = "INSERT INTO rentals (id, client_cnp, vehicle_plate, start_date, end_date, returned) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, rental.getId());
            stmt.setString(2, rental.getClient().getCnp());
            stmt.setString(3, rental.getVehicle().getPlateNumber());
            stmt.setDate(4, Date.valueOf(rental.getStartDate()));
            stmt.setDate(5, Date.valueOf(rental.getEndDate()));
            stmt.setBoolean(6, rental.isReturned());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare creare inchiriere: " + e.getMessage());
        }
    }

    public List<Rental> readAll(List<Client> clients, List<Vehicle> vehicles) {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM rentals";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String cnp = rs.getString("client_cnp");
                String plate = rs.getString("vehicle_plate");
                
                Client c = clients.stream().filter(cl -> cl.getCnp().equals(cnp)).findFirst().orElse(null);
                Vehicle v = vehicles.stream().filter(veh -> veh.getPlateNumber().equals(plate)).findFirst().orElse(null);
                
                if (c != null && v != null) {
                    Rental r = new Rental((UUID) rs.getObject("id"), c, v, rs.getDate("start_date").toLocalDate(), rs.getDate("end_date").toLocalDate());
                    r.setReturned(rs.getBoolean("returned"));
                    rentals.add(r);
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare citire inchirieri: " + e.getMessage());
        }
        return rentals;
    }

    public void update(Rental rental) {
        String sql = "UPDATE rentals SET returned=?, client_cnp=?, vehicle_plate=?, start_date=?, end_date=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, rental.isReturned());
            stmt.setString(2, rental.getClient().getCnp());
            stmt.setString(3, rental.getVehicle().getPlateNumber());
            stmt.setDate(4, Date.valueOf(rental.getStartDate()));
            stmt.setDate(5, Date.valueOf(rental.getEndDate()));
            stmt.setObject(6, rental.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare update inchiriere: " + e.getMessage());
        }
    }

    public void delete(UUID id) {
        String sql = "DELETE FROM rentals WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare stergere inchiriere: " + e.getMessage());
        }
    }
}
