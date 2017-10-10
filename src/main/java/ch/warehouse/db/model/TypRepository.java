package ch.warehouse.db.model;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TypRepository extends CrudRepository<Typ, UUID> {
}
