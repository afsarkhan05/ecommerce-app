package com.zubi.ecommerce.user.controller;

import com.zubi.ecommerce.auth.common.service.UserDetailsImpl;
import com.zubi.ecommerce.user.dto.UserDTO;
import com.zubi.ecommerce.user.model.User;
import com.zubi.ecommerce.user.repository.UserRepository;
import com.zubi.ecommerce.user.security.jwt.JwtTokenCreatorUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;



    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenCreatorUtil jwtTokenCreatorUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDTO loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok().body(jwtTokenCreatorUtils.generateTokenFromUsername(userDetails.getUsername(), userDetails.getId().toString()));

        //ResponseCookie jwtCookie = jwtTokenCreatorUtils.generateJwtCookie(userDetails);


        /*return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(UserDTO.builder()
                        .userName(userDetails.getUsername())
                        .userId(userDetails.getId())
                        .email(userDetails.getEmail())
                        .build());*/

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO signUpRequest) {
        Optional<Long> optionalLong = userRepository.existsByUserName(signUpRequest.getUserName());
        if (! optionalLong.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        Optional<String> optionalS = userRepository.existsByEmail(signUpRequest.getEmail());
        if (! optionalS.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = signUpRequest.toUser();
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtTokenCreatorUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }
}