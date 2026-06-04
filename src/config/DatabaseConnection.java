package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/paoj";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private DatabaseConnection() {
        createDatabaseIfNotExists();
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            createTables();
        } catch (SQLException e) {
            System.err.println("Eroare conexiune DB: " + e.getMessage());
        }
    }

    private void createDatabaseIfNotExists() {
        String defaultUrl = "jdbc:postgresql://localhost:5432/postgres";
        try (Connection conn = DriverManager.getConnection(defaultUrl, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = 'paoj'");
            if (!rs.next()) {
                stmt.executeUpdate("CREATE DATABASE paoj");
                System.out.println("Baza de date 'paoj' a fost creata automat.");
            }
        } catch (SQLException e) {
            System.err.println("Avertisment la crearea bazei de date (poate exista deja sau permisiuni insuficiente): " + e.getMessage());
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void createTables() {
        String createClients = "CREATE TABLE IF NOT EXISTS clients (" +
                "cnp VARCHAR(20) PRIMARY KEY," +
                "name VARCHAR(100)," +
                "email VARCHAR(100)," +
                "phone VARCHAR(20)," +
                "license_categories VARCHAR(200))";

        String createVehicles = "CREATE TABLE IF NOT EXISTS vehicles (" +
                "plate_number VARCHAR(20) PRIMARY KEY," +
                "brand VARCHAR(50)," +
                "model VARCHAR(50)," +
                "price_per_day DOUBLE PRECISION," +
                "available BOOLEAN," +
                "required_category VARCHAR(10)," +
                "vehicle_type VARCHAR(20)," +
                "extra_param DOUBLE PRECISION)";

        String createRentals = "CREATE TABLE IF NOT EXISTS rentals (" +
                "id UUID PRIMARY KEY," +
                "client_cnp VARCHAR(20) REFERENCES clients(cnp)," +
                "vehicle_plate VARCHAR(20) REFERENCES vehicles(plate_number)," +
                "start_date DATE," +
                "end_date DATE," +
                "returned BOOLEAN)";

        String createLocations = "CREATE TABLE IF NOT EXISTS locations (" +
                "id UUID PRIMARY KEY," +
                "name VARCHAR(100)," +
                "address VARCHAR(200)," +
                "studio_type VARCHAR(50))";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createClients);
            stmt.execute(createVehicles);
            stmt.execute(createRentals);
            stmt.execute(createLocations);
        } catch (SQLException e) {
            System.err.println("Eroare creare tabele: " + e.getMessage());
        }
    }
}
