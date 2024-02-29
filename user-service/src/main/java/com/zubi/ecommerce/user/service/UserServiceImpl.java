package com.zubi.ecommerce.user.service;

import com.zubi.ecommerce.user.dto.UserDTO;
import com.zubi.ecommerce.user.model.User;
import com.zubi.ecommerce.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDTO> get(Long userId) {
        return Optional.of(userRepository.findById(userId)
                .map(User::toUserDTO)
                .orElseThrow(EntityNotFoundException::new));

        /*return Optional.of(UserDTO.builder()
                .userId(userId)
                .userName("name_"+userId)
                .build());*/
    }

    public User insertUser(UserDTO dto){
        return dto.toUser();
    }
}
