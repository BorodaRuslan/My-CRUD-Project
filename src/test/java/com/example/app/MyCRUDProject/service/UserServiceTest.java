package com.example.app.MyCRUDProject.service;

import com.example.app.MyCRUDProject.entity.User;
import com.example.app.MyCRUDProject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;


    @Test
    void returnOfAllUsers(){
        User user1 = User.builder()
                .id(1L)
                .firstName("Ruslan")
                .lastName("Boroda")
                .phone("8063-57-66-165")
                .build();

        User user2 = User.builder()
                .id(2L)
                .firstName("Кристинка")
                .lastName("Путанка")
                .phone("8063-123-54-32")
                .build();

        List<User> userList = List.of(user1, user2);
        Mockito.when(repository.findAll()).thenReturn(userList);
        List<User> result = service.getAllUsers();

        assertEquals(userList, result);
    }
    @Test
    void returnOfAllUsers_negative(){
        List<User> userList = Collections.emptyList();
        Mockito.when(repository.findAll()).thenReturn(userList);
        List<User> result = service.getAllUsers();

        assertEquals(userList, result);
    }
    @Test
    void returnOfAllUsers_Exception(){
        Mockito.when(repository.findAll()).thenThrow(new RuntimeException("Error while getting users"));
        List<User> result = service.getAllUsers();

        // Нужно разобраться в этом деле!
        assertNotNull(result);
        assertTrue(result.isEmpty());

    }
}
