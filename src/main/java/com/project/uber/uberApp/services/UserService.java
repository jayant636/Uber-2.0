package com.project.uber.uberApp.services;

import com.project.uber.uberApp.entities.User;
import com.project.uber.uberApp.repositories.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserService implements UserDetailsService {

    private final UserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRespository.findByEmail(username).orElse(null);
    }

    public User getUserById(Long userId) {

        return userRespository.findById(userId).orElseThrow(()->new RuntimeException("User does not exist with this userid"+userId));

    }
}
