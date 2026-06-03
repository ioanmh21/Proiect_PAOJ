package repository;

import config.DatabaseConnection;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {
    private static VehicleRepository instance;
    private Connection connection;

    private VehicleRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static synchronized VehicleRepository getInstance() {
        if (instance == null) {
            instance = new VehicleRepository();
        }
        return instance;
    }

    public void create(Vehicle v) {
        String sql = "INSERT INTO vehicles (plate_number, brand, model, price_per_day, available, required_category, vehicle_type, extra_param) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, v.getPlateNumber());
            stmt.setString(2, v.getBrand());
            stmt.setString(3, v.getModel());
            stmt.setDouble(4, v.getPricePerDay());
            stmt.setBoolean(5, v.isAvailable());
            stmt.setString(6, v.getRequiredLicenseCategory().name());
            stmt.setString(7, v.getVehicleType().name());
            
            double extraParam = 0;
            if (v instanceof Car) extraParam = ((Car) v).getNumberOfSeats();
            else if (v instanceof Truck) extraParam = ((Truck) v).getMaxLoadCapacityTons();
            else if (v instanceof TrailerTruck) extraParam = ((TrailerTruck) v).getTotalLengthMeters();
            else if (v instanceof Bus) extraParam = ((Bus) v).getPassengerCapacity();
            else if (v instanceof Motorcycle) extraParam = ((Motorcycle) v).hasSidecar() ? 1 : 0;
            
            stmt.setDouble(8, extraParam);
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (!e.getMessage().contains("duplicate key")) {
                System.err.println("Eroare la adaugarea vehiculului: " + e.getMessage());
            }
        }
    }

    public List<Vehicle> readAll() {
        List<Vehicle> fleet = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String plate = rs.getString("plate_number");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                double price = rs.getDouble("price_per_day");
                boolean avail = rs.getBoolean("available");
                LicenseCategory cat = LicenseCategory.valueOf(rs.getString("required_category"));
                VehicleType type = VehicleType.valueOf(rs.getString("vehicle_type"));
                double extra = rs.getDouble("extra_param");

                Vehicle v = null;
                switch (type) {
                    case CAR: v = new Car(brand, model, plate, price, (int)extra); break;
                    case TRUCK: v = new Truck(brand, model, plate, price, extra); break;
                    case TRAILER_TRUCK: v = new TrailerTruck(brand, model, plate, price, extra); break;
                    case BUS: v = new Bus(brand, model, plate, price, (int)extra); break;
                    case MOTORCYCLE: v = new Motorcycle(brand, model, plate, price, cat, extra == 1.0); break;
                }
                if (v != null) {
                    v.setAvailable(avail);
                    fleet.add(v);
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare citire vehicule: " + e.getMessage());
        }
        return fleet;
    }

    public void update(Vehicle v) {
        String sql = "UPDATE vehicles SET available=? WHERE plate_number=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, v.isAvailable());
            stmt.setString(2, v.getPlateNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare update vehicul: " + e.getMessage());
        }
    }

    public void delete(String plate) {
        String sql = "DELETE FROM vehicles WHERE plate_number=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, plate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare stergere vehicul: " + e.getMessage());
        }
    }
}
