package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Length;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.UUID;

public interface LengthRestRepository extends DataTablesRepository<Length, UUID> {

}
