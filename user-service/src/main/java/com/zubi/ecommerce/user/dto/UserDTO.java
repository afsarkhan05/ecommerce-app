package com.zubi.ecommerce.user.dto;

import com.zubi.ecommerce.user.model.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {

    private Long userId;
    private String userName;

    private String email;

    private String password;


    public User toUser(){
        return User.builder()
                .id(getUserId())
                .userName(getUserName())
                .email(getEmail())
                .build();
    }
}
