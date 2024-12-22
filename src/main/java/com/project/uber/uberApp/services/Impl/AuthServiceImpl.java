package com.project.uber.uberApp.services.Impl;

import com.project.uber.uberApp.dtos.DriverDto;
import com.project.uber.uberApp.dtos.SignupDto;
import com.project.uber.uberApp.dtos.UserDto;
import com.project.uber.uberApp.entities.User;
import com.project.uber.uberApp.entities.enums.Roles;
import com.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.project.uber.uberApp.repositories.UserRespository;
import com.project.uber.uberApp.services.AuthService;
import com.project.uber.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRespository userRespository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public UserDto signup(SignupDto signupDto) {

        //Checking existing user
        User existingUser = userRespository.findByEmail(signupDto.getEmail()).orElse(null);

        if(existingUser != null){
            throw new RuntimeConflictException("User already exists cannot signup with email" + signupDto.getEmail());
        }

        //All the things that needs to created for user will be done here
        User user = modelMapper.map(signupDto, User.class);
        //By default any user signup as rider only
        user.setRoles(Set.of(Roles.RIDER));
        User savedUser = userRespository.save(user);

        // Created user related entities
        riderService.createNewRider(savedUser);

//        TODO : Add wallet related service here

//        converting user entity to user dto
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId) {
        return null;
    }
}
