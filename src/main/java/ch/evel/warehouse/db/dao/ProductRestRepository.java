package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Location;
import ch.evel.warehouse.db.model.Product;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRestRepository extends DataTablesRepository<Product, UUID> {
    List<Product> findAllByLocation(Location location);
}
