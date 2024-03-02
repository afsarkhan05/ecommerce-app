package com.zubi.ecommerce.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private Long id;
    private String username;
    private String email;
}
