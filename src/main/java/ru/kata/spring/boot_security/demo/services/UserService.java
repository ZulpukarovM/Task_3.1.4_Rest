package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
public interface UserService {
    User getUser(long id);

    boolean add(User user);

    void update(long id, User userToBeUpdated);

    void delete(long id);

    List<User> getAllUsers();
}
