package com.danidev.shiftmanager.dto;


import lombok.Setter;

@Setter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
}
