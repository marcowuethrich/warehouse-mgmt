package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    User findUserByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
