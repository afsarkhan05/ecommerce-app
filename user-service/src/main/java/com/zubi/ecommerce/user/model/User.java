package com.zubi.ecommerce.user.model;

import com.zubi.ecommerce.user.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_detail")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {


    @Column( name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "userName")
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    private String status;

    public UserDTO toUserDTO(){
        return UserDTO.builder()
                .userId(this.getId())
                .userName(getUserName())
                .password(getPassword())
                .email(getEmail())
                .build();
    }
}
