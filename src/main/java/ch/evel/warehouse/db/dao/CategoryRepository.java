package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoryRepository extends CrudRepository<Category, UUID> {
    boolean existsByName(String name);

    boolean existsByCode(String code);
}
