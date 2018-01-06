package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Groups;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GroupRepository extends CrudRepository<Groups, UUID> {
    boolean existsByName(String name);
    boolean existsByCode(String code);
}
