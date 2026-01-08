package com.danidev.shiftmanager.service;

import com.danidev.shiftmanager.dto.UserCreateRequest;
import com.danidev.shiftmanager.dto.UserResponse;
import com.danidev.shiftmanager.entity.User;

public interface UserService {
    UserResponse create(UserCreateRequest request);
}
