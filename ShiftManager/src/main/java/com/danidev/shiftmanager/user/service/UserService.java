package com.danidev.shiftmanager.user.service;

import com.danidev.shiftmanager.user.dto.UserCreateRequest;
import com.danidev.shiftmanager.user.dto.UserResponse;

public interface UserService {
    UserResponse create(UserCreateRequest request);
}
