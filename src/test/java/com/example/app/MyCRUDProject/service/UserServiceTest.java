package com.example.app.MyCRUDProject.service;

import com.example.app.MyCRUDProject.entity.User;
import com.example.app.MyCRUDProject.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;


    @Test
    void testGetOfAllUsers(){
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
    void testGetAllUsers_NoUsers(){
        List<User> userList = Collections.emptyList();
        Mockito.when(repository.findAll()).thenReturn(userList);
        // 2. Вызов метода, который мы хотим протестировать
        List<User> result = service.getAllUsers();

        assertEquals(userList, result);
    }

    @Test
    void testGetUserById_WhenIdExists(){
        // 1. Подготовка данных к тесту: Создайте пользователя и добавьте его в репозиторий
        User user = User.builder()
                .id(1L)
                .firstName("Ruslan")
                .lastName("Boroda")
                .phone("8063-57-66-165")
                .build();

        Mockito.when(repository.existsById(1L)).thenReturn(true);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(user));
        // 2. Вызов метода, который мы хотим протестировать
        Optional<User> result = service.getUserById(1L);

        // Тест

        assertTrue(result.isPresent());
//        assertEquals(user, result.get());

        User resultUser = result.get();

        // Проверяем, что объект пользователя не является null
        assertNotNull(resultUser);

        assertEquals(user.getId(), resultUser.getId());
        assertEquals(user.getFirstName(), resultUser.getFirstName());
        assertEquals(user.getLastName(), resultUser.getLastName());
        assertEquals(user.getPhone(), resultUser.getPhone());


    }

    @Test
    void testGetUserById_WhenIdDoesNotExist(){
        // Подготовка данных к тесту: Не существующий id
        Mockito.when(repository.existsById(2L)).thenReturn(false);

        // Вызов метода, который мы хотим протестировать
        Optional<User> result = service.getUserById(2L);

        // Утверждение (assertion) для проверки, что метод вернул пустой Optional
        assertTrue(result.isEmpty());


    }

    @Test
    void testCreateUser_Positive(){
       // Подготовка данных к тесту: Создаем пользователя и настраиваем мок репозиторий
        User user = User.builder()
                .id(1L)
                .firstName("Ruslan")
                .lastName("Boroda")
                .phone("8063-57-66-142")
                .build();
        Mockito.when(repository.save(user)).thenReturn(user);

        // Вызов метода, который мы хотим протестировать
        Optional<User> resultOptional = service.createUser(user);

        assertTrue(resultOptional.isPresent());
        User result = resultOptional.get();
        assertNotNull(result);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getPhone(), result.getPhone());


    }

    @Test
    void testCreateUser_WhenUserIsInvalid(){
        // Подготовка данных к тесту: Создаем пользователя, который не может быть сохранен
        User user = User.builder()
                .firstName("Ruslan")
                .lastName("Boroda")
                .phone("8063-57-66-142")
                .build();

        Mockito.when(repository.save(user)).thenThrow(new DataIntegrityViolationException("Incorrect data"));
        // Вызов метода, который мы хотим протестировать
        Optional<User> result = service.createUser(user);

        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateUserNumberPhone_WhenIdExists(){
        Long userId = 1L;
        String newUserPhone = "8068-33-154-22";

        User user = User.builder()
                .id(userId)
                .firstName("Ruslan")
                .lastName("Boroda")
                .phone("8063-57-66-165")
                .build();
        Mockito.when(repository.existsById(userId)).thenReturn(true);
        Mockito.when(repository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(repository.save(user)).thenReturn(user);

        Optional<?> result = service.updateUserNumberPhone(userId, newUserPhone);

        assertTrue(result.isPresent());
        User userResult =(User) result.get();
        assertNotNull(userResult);
        assertEquals(newUserPhone, userResult.getPhone());
    }

    @Test
    void testUpdateUserNumberPhone_WhenIdDoesNotExist(){
        // Подготовка данных к тесту: ID не существует
        Long userId = 3L;
        String newUserPhone = "123-456-7890";

        Mockito.when(repository.existsById(userId)).thenReturn(false);
        // Вызов метода, который мы хотим протестировать
        Optional<?> result = service.updateUserNumberPhone(userId, newUserPhone);

        // убеждаемся, что Optional пуст, так как ID не существует!
        assertTrue(result.isEmpty());

    }

    @Test
    void testDeleteUser_WhenIdExists(){
        Long userId = 1L;
        Mockito.when(repository.existsById(userId)).thenReturn(true);

       // Вызов метода, который мы хотим протестировать
        boolean result = service.deleteUserById(userId);
        assertTrue(result);
    }

    @Test
    void testDeleteUser_WhenIdDoesNotExists(){
        Long userId = 3L;
        Mockito.when(repository.existsById(userId)).thenReturn(false);
        // Вызов метода, который мы хотим протестировать
        boolean result = service.deleteUserById(userId);
        assertFalse(result);
    }




}
