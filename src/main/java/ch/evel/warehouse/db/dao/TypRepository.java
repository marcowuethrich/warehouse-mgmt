package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Typ;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TypRepository extends CrudRepository<Typ, UUID> {
    boolean existsByName(String name);
    boolean existsByCode(String code);

    List<Typ> findAllByOrderByCodeAsc();
}
