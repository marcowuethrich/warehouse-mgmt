package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface LocationRepository extends CrudRepository<Location, UUID> {
    boolean existsByName(String name);
    List<Location> findAllByOrderByNameAsc();
}