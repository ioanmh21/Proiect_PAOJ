package repository;

import config.DatabaseConnection;
import model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LocationRepository {
    private static LocationRepository instance;
    private Connection connection;

    private LocationRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static synchronized LocationRepository getInstance() {
        if (instance == null) {
            instance = new LocationRepository();
        }
        return instance;
    }

    public void create(Location loc) {
        String sql = "INSERT INTO locations (id, name, address, studio_type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, loc.getId());
            stmt.setString(2, loc.getName());
            stmt.setString(3, loc.getAddress());
            stmt.setString(4, loc.getStudioType());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare creare locatie: " + e.getMessage());
        }
    }

    public List<Location> readAll() {
        List<Location> locs = new ArrayList<>();
        String sql = "SELECT * FROM locations";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                locs.add(new Location(
                        (UUID) rs.getObject("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("studio_type")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Eroare citire locatii: " + e.getMessage());
        }
        return locs;
    }

    public void update(Location loc) {
        String sql = "UPDATE locations SET name=?, address=?, studio_type=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, loc.getName());
            stmt.setString(2, loc.getAddress());
            stmt.setString(3, loc.getStudioType());
            stmt.setObject(4, loc.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare update locatie: " + e.getMessage());
        }
    }

    public void delete(UUID id) {
        String sql = "DELETE FROM locations WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare stergere locatie: " + e.getMessage());
        }
    }
}
