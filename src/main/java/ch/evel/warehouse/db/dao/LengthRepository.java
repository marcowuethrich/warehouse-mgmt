package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Length;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LengthRepository extends CrudRepository<Length, UUID> {
    boolean existsBySize(double size);

    boolean existsByCode(String code);
}
