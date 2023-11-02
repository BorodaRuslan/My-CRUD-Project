package com.example.app.MyCRUDProject.repository;

import com.example.app.MyCRUDProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findByIdMyMethod(@NonNull @Param("userId") Long userId);



}
