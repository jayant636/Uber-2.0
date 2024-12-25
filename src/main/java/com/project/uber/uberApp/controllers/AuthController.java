package com.project.uber.uberApp.controllers;

import com.project.uber.uberApp.dtos.DriverDto;
import com.project.uber.uberApp.dtos.OnBoardDriverDto;
import com.project.uber.uberApp.dtos.SignupDto;
import com.project.uber.uberApp.dtos.UserDto;
import com.project.uber.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/signup")
    ResponseEntity<UserDto> signup(@RequestBody SignupDto signupDto){
        return new ResponseEntity<>(authService.signup(signupDto), HttpStatus.CREATED) ;
    }

    @PostMapping(path = "/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId , @RequestBody OnBoardDriverDto onBoardDriverDto){
       return new ResponseEntity<>(authService.onboardNewDriver(userId, onBoardDriverDto.getVehicleId()),HttpStatus.CREATED);
    }


}
