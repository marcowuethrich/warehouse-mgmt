package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Category;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.UUID;

public interface CategoryRestRepository extends DataTablesRepository<Category, UUID> {
}
