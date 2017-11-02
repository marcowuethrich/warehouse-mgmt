package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Color;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ColorRepository extends CrudRepository<Color, UUID> {
}