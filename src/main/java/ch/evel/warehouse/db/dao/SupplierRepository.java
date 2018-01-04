package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Supplier;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SupplierRepository extends CrudRepository<Supplier, UUID> {
    boolean existsByName(String name);

    boolean existsByCode(String code);
}
