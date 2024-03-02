package com.zubi.ecommerce.user.repository;


import com.zubi.ecommerce.user.model.User;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("select a from User a where userName = :userName ")
    Optional<User> findByUserName(@org.springframework.data.repository.query.Param("userName") String userName);


    @Query("select a.id from User a where userName = :userName ")
    Optional<Long> existsByUserName(@org.springframework.data.repository.query.Param("userName") String userName);

    @Query("select a.email from User a where email = :email ")
    Optional<String> existsByEmail(@org.springframework.data.repository.query.Param("email") String email);
    
    @Query("select a from User a where a.id = :userId ")
    Optional<User> findById(@Param("userId") Long userId);
}
