package repository;

import config.DatabaseConnection;
import model.Client;
import model.LicenseCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientRepository {
    private static ClientRepository instance;
    private Connection connection;

    private ClientRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static synchronized ClientRepository getInstance() {
        if (instance == null) {
            instance = new ClientRepository();
        }
        return instance;
    }

    public void create(Client client) {
        String sql = "INSERT INTO clients (cnp, name, email, phone, license_categories) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, client.getCnp());
            stmt.setString(2, client.getName());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getPhone());
            
            String categories = client.getLicenseCategories().stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
            stmt.setString(5, categories);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Poate exista deja
            if (!e.getMessage().contains("duplicate key")) {
                System.err.println("Eroare la adaugarea clientului: " + e.getMessage());
            }
        }
    }

    public List<Client> readAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Client c = new Client(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("cnp")
                );
                String catStr = rs.getString("license_categories");
                if (catStr != null && !catStr.isEmpty()) {
                    Arrays.stream(catStr.split(","))
                            .map(LicenseCategory::valueOf)
                            .forEach(c::addLicenseCategory);
                }
                clients.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Eroare citire clienti: " + e.getMessage());
        }
        return clients;
    }

    public void update(Client client) {
        String sql = "UPDATE clients SET name=?, email=?, phone=?, license_categories=? WHERE cnp=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getPhone());
            String categories = client.getLicenseCategories().stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
            stmt.setString(4, categories);
            stmt.setString(5, client.getCnp());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare update client: " + e.getMessage());
        }
    }

    public void delete(String cnp) {
        String sql = "DELETE FROM clients WHERE cnp=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cnp);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare stergere client: " + e.getMessage());
        }
    }
}
