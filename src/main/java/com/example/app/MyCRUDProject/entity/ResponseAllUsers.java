package com.example.app.MyCRUDProject.entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAllUsers {
    String status;
    String massage;
    List<User> userList;
}
