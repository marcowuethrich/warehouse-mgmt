package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.TypGroup;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.UUID;

public interface TypGroupRepository extends DataTablesRepository<TypGroup, UUID> {
}
