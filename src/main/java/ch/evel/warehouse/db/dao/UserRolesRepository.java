package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface UserRolesRepository extends CrudRepository<UserRole, Long> {

	List<UserRole> findUserRoleById(UUID uuid);

    UserRole findByRole(String role);
}