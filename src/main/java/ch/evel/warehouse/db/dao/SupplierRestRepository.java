package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Supplier;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.UUID;

public interface SupplierRestRepository extends DataTablesRepository<Supplier, UUID> {

}
