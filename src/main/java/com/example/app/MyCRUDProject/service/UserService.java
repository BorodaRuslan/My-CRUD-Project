package com.example.app.MyCRUDProject.service;
import com.example.app.MyCRUDProject.entity.User;
import com.example.app.MyCRUDProject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository repository;

    public List<User> getAllUsers() {
        List<User> allUsers = null;
        try {
            allUsers = repository.findAll();
            if (allUsers.isEmpty())
                return Collections.emptyList();
        } catch (Exception e) {
            LOGGER.error("Error while getting users" + e);
        }
        return allUsers;
    }

    public Optional<User> getUserById(Long id) {
        if (repository.existsById(id))
            return repository.findById(id);
        else
            return Optional.empty();
    }

    public Optional<User> createUser(User user){
        try {
            User userResult = repository.save(user);
            return Optional.of(userResult);
        } catch (Exception e){
            LOGGER.error("Something went wrong!" + e);
            return Optional.empty();
        }
    }
    public Optional<?> updateUserNumberPhone(Long id, String phone){
        if (repository.existsById(id)){
            Optional<User> userOptions = repository.findById(id);
            User user = userOptions.get();
            user.setPhone(phone);
            return Optional.of(repository.save(user));
        }
        return Optional.empty();
    }

    public Optional<?> deleteUserById(Long id) {
        if (repository.existsById(id)) {
            Optional<User> user = repository.findById(id);
            repository.deleteById(id);
            return Optional.of(user);
        }
        return Optional.empty();
    }
}