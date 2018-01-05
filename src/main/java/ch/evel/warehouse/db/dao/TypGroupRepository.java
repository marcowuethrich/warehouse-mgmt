package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.TypGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TypGroupRepository extends JpaRepository<TypGroup, UUID> {
    boolean existsByName(String name);
    boolean existsByCode(String code);
}
