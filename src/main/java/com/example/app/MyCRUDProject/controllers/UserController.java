package com.example.app.MyCRUDProject.controllers;

import com.example.app.MyCRUDProject.entity.Response;
import com.example.app.MyCRUDProject.entity.User;
import com.example.app.MyCRUDProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService service;

    public static final String SHOW_ALL_USERS = "/api/all";
    public static final String FIND_USER_BY_ID = "/api/find/{id}";
    public static final String CREATE_USER = "/api/create/{firstName}/{lastName}/{phone}";
    public static final String UPDATE_USER_PHONE = "/api/update/phone/{id}/{phone}";
    public static final String DELETE_USER = "/api/delete/{id}";


    @GetMapping(SHOW_ALL_USERS)
    public ResponseEntity<List<User>> showAllUsers(){
        List<User> allUsers = service.getAllUsers();
        return ResponseEntity.ok(allUsers);

    }

    @GetMapping(FIND_USER_BY_ID)
    public ResponseEntity<?> findById(@PathVariable("id") Long userId){
        Optional<User> optionalUsers = service.getUserById(userId);
        if (optionalUsers.isPresent()){
            User user = optionalUsers.get();
            return ResponseEntity.ok(Response.builder()
                    .status("Success")
                    .massage("User successfully found ")
                    .user(user));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.builder()
                            .status("Error")
                            .massage("user is not found")
                            .user(null));
    }

    @PostMapping(CREATE_USER)
    public ResponseEntity<?> createUser(
            @PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName,
            @PathVariable("phone") String phone){

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .build();

        Optional<User> userResult = service.createUser(user);
        if (userResult.isPresent())
            return ResponseEntity.ok(Response.builder()
                    .status("Success")
                    .massage("User successfully saved")
                    .user(user)
                    .build());

        else
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(Response.builder()
                            .status("Error")
                            .massage("User was not saved in the database")
                            .user(null)
                            .build());

    }
    @PatchMapping(UPDATE_USER_PHONE)
    public ResponseEntity<?> update(@PathVariable("id") Long id, @PathVariable("phone") String phone){
        Optional<?> updatedUser = service.updateUserNumberPhone(id, phone);
        if (updatedUser.isPresent()){
            User user = (User) updatedUser.get();

            return ResponseEntity.ok(Response.builder()
                    .status("Success")
                    .massage("Data successfully updated")
                    .user(user)
                    .build());
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.builder()
                            .status("Error")
                            .massage("User with specified ID not found")
                            .user(null)
                            .build());
    }
    @DeleteMapping(DELETE_USER)
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        Optional<?> userDelete = service.deleteUserById(id);

        if (userDelete.isPresent()) {
            User user = (User) userDelete.get();
            return ResponseEntity.ok(Response.builder()
                    .status("Success")
                    .massage("Successfully uninstalled user ")
                    .user(user)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .status("Error")
                        .massage("No such user exists ")
                        .user(null)
                        .build());
    }


//    @GetMapping(FIND_USER_BY_ID)
//    public ResponseEntity<?> findById(@PathVariable("id") Long userId){
//        Optional<User> user = service.getUserById(userId);
//        return user.map(value -> ResponseEntity.ok(Response.builder()
//                .status("Success")
//                .massage("User successfully found ")
//                .user(value))).orElseGet(() -> ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(Response.builder()
//                        .status("Error")
//                        .massage("user is not found")
//                        .user(null)));
//    }




}
