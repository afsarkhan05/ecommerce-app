package com.zubi.ecommerce.user.service;

import com.zubi.ecommerce.user.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    public Optional<UserDTO> get(Long userId);
}
