package com.example.app.MyCRUDProject.controllers;

import com.example.app.MyCRUDProject.entity.Response;
import com.example.app.MyCRUDProject.entity.ResponseAllUsers;
import com.example.app.MyCRUDProject.entity.User;
import com.example.app.MyCRUDProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    UserService service;

    public static final String SHOW_ALL_USERS = "/api/all";
    public static final String FIND_USER_BY_ID = "/api/find/{id}";
    public static final String CREATE_USER = "/api/create/{firstName}/{lastName}/{phone}";
    public static final String UPDATE_USER_PHONE = "/api/update/phone/{id}/{phone}";
    public static final String DELETE_USER = "/api/delete/{id}";


    @GetMapping(SHOW_ALL_USERS)
    public ResponseEntity<ResponseAllUsers> showAllUsers(){
        List<User> allUsers = service.getAllUsers();
        ResponseAllUsers response = ResponseAllUsers.builder()
                .status("Success")
                .massage("All users found")
                .userList(allUsers)
                .build();
        return ResponseEntity.ok(response);

    }

    @GetMapping(FIND_USER_BY_ID)
    public ResponseEntity<Response> findById(@PathVariable("id") Long userId){
        Optional<User> optionalUsers = service.getUserById(userId);
        if (optionalUsers.isPresent()){
            User user = optionalUsers.get();
            Response response = Response.builder()
                    .status("Success")
                    .massage("User successfully find")
                    .user(user)
                    .build();
            return ResponseEntity.ok(response);


        } else {
            Response response = Response.builder()
                    .status("Error")
                    .massage("user is not found")
                    .user(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping(CREATE_USER)
    public ResponseEntity<Response> createUser(
            @PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName,
            @PathVariable("phone") String phone){

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .build();

        Optional<User> userResult = service.createUser(user);
        if (userResult.isPresent()) {
            Response response = Response.builder()
                    .status("Success")
                    .massage("User successfully saved")
                    .user(user)
                    .build();
        return ResponseEntity.ok(response);
        }
        else {

            Response response = Response.builder()
                    .status("Error")
                    .massage("User was not saved in the database")
                    .user(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }
    @PatchMapping(UPDATE_USER_PHONE)
    public ResponseEntity<Response> update(@PathVariable("id") Long id, @PathVariable("phone") String phone){
        Optional<?> updatedUser = service.updateUserNumberPhone(id, phone);
        if (updatedUser.isPresent()){
            User user = (User) updatedUser.get();
            Response response = Response.builder()
                    .status("Success")
                    .massage("Data successfully updated")
                    .user(user)
                    .build();
            return ResponseEntity.ok(response);
        }
        else {
            Response response = Response.builder()
                    .status("Error")
                    .massage("User with specified ID not found")
                    .user(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @DeleteMapping(DELETE_USER)
    public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id) {
        boolean userDelete = service.deleteUserById(id);
        if (userDelete) {
            Response response = Response.builder()
                    .status("Success")
                    .massage("Successfully uninstalled user ")
                    .user(null)
                    .build();
            return ResponseEntity.ok(response);

        } else {
            Response response = Response.builder()
                    .status("Error")
                    .massage("No such user exists")
                    .user(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
