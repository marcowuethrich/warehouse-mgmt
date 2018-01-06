package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Product;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.UUID;

public interface ProductRestRepository extends DataTablesRepository<Product, UUID> {
}
