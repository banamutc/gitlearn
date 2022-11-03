package com.example.springfile.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRequest {
    private String username;
    private String password;

}
