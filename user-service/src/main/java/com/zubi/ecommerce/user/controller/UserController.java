package com.zubi.ecommerce.user.controller;

import com.zubi.ecommerce.user.service.UserService;
import com.zubi.ecommerce.user.utils.AppConstants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable(value = AppConstants.ID, required = true) Long userId) {
        // User registration logic
        return userService.get(userId).map(this::ok).orElseThrow(EntityNotFoundException::new);
        //return ResponseEntity.ok(userService.get(userId));
    }

    private ResponseEntity ok(Object list){
        return new ResponseEntity(list, HttpStatus.OK);
    }

    // Additional endpoints for login, profile updates, etc.
}
