package ch.warehouse.db.model;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LengthRepository extends CrudRepository<Length, UUID> {
}
