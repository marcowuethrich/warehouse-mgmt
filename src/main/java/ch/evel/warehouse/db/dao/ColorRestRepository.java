package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Color;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.UUID;

public interface ColorRestRepository extends DataTablesRepository<Color, UUID> {

}
