package com.example.app.MyCRUDProject.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    String status;
    String massage;
    User user;
}
