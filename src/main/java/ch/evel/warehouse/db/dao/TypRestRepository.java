package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Typ;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.UUID;

public interface TypRestRepository extends DataTablesRepository<Typ, UUID> {
}
