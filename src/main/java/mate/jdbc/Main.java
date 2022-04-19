package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("~/IdeaProjects/jv-jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Name");
        manufacturer.setCountry("Country");
        manufacturerDao.create(manufacturer);
        Optional<Manufacturer> getManufacturer = manufacturerDao.get(1L);
        List<Manufacturer> all = manufacturerDao.getAll();
        manufacturer.setName("Bob");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(1L);
    }
}
