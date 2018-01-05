package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.TypGroup;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.List;
import java.util.UUID;

public interface TypGroupRepository extends DataTablesRepository<TypGroup, UUID> {
    boolean existsByName(String name);
    boolean existsByCode(String code);

    List<TypGroup> findAll();
}
