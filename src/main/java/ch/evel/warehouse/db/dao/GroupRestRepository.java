package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Groups;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.UUID;

public interface GroupRestRepository extends DataTablesRepository<Groups, UUID> {
}
