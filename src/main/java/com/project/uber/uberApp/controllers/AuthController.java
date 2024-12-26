package com.project.uber.uberApp.controllers;

import com.project.uber.uberApp.dtos.*;
import com.project.uber.uberApp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

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

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){
       String tokens[] = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        Cookie cookie = new Cookie("token",tokens[1]);
        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);

       return ResponseEntity.ok(new LoginResponseDto(tokens[0]));
    }


}
