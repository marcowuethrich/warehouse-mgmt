package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Location;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.UUID;

public interface LocationRestRepository extends DataTablesRepository<Location, UUID> {
}
