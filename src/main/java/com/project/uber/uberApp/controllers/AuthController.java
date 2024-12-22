package com.project.uber.uberApp.controllers;

import com.project.uber.uberApp.dtos.SignupDto;
import com.project.uber.uberApp.dtos.UserDto;
import com.project.uber.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/signup")
    UserDto signup(@RequestBody SignupDto signupDto){
        return authService.signup(signupDto);
    }

}
