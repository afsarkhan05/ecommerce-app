package com.zubi.ecommerce.user.model;

import com.zubi.ecommerce.user.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Long id;

    @Column(name ="name")
    private String name;

    public UserDTO toUserDTO(){
        return UserDTO.builder()
                .userName(this.getName())
                .userId(this.getId())
                .build();
    }
}
