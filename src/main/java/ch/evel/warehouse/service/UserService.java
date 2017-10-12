package ch.evel.warehouse.service;


import ch.evel.warehouse.db.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findUserByUsername(String username);
}
