package com.zubi.ecommerce.user.service;

import com.zubi.ecommerce.auth.common.service.UserDetailsImpl;
import com.zubi.ecommerce.user.model.User;
import com.zubi.ecommerce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.
                builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUserName())
                .password(user.getPassword())
                .build();
    }
}
