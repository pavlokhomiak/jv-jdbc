package mate.jdbc.dao;

import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "insert into manufacturers(country, name, is_deleted) values(?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement =
                     connection.prepareStatement(insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getCountry());
            createManufacturerStatement.setString(2, manufacturer.getName());
            createManufacturerStatement.setBoolean(3, false);
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectRequest = "select * from manufacturers where is_deleted = false and id = " + id;
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = getManufacturerStatement
                    .executeQuery(selectRequest);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            manufacturer.setId(id);
            manufacturer.setCountry(country);
            manufacturer.setName(name);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB", e);
        }
        return Optional.of(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectRequest = "select * from manufacturers where is_deleted = false";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturerStatement
                    .executeQuery(selectRequest);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setCountry(country);
                manufacturer.setName(name);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "update manufacturers set name = ?, country = ? where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateManufacturerStatement =
                     connection.prepareStatement(updateManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "update manufacturers set is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteManufacturerStatement =
                     connection.prepareStatement(deleteManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer", e);
        }
    }
}
