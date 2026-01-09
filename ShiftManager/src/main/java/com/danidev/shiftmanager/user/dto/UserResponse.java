package com.danidev.shiftmanager.user.dto;


import lombok.Setter;

@Setter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
}
