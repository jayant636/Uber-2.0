package com.project.uber.uberApp.services.Impl;

import com.project.uber.uberApp.dtos.DriverDto;
import com.project.uber.uberApp.dtos.SignupDto;
import com.project.uber.uberApp.dtos.UserDto;
import com.project.uber.uberApp.entities.Driver;
import com.project.uber.uberApp.entities.User;
import com.project.uber.uberApp.entities.enums.Roles;
import com.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.project.uber.uberApp.repositories.UserRespository;
import com.project.uber.uberApp.services.AuthService;
import com.project.uber.uberApp.services.DriverService;
import com.project.uber.uberApp.services.RiderService;
import com.project.uber.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.project.uber.uberApp.entities.enums.Roles.DRIVER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRespository userRespository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;


    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
//    Either everything will be implemented or nothing
    @Transactional
    public UserDto signup(SignupDto signupDto) {

        //Checking existing user
        User existingUser = userRespository.findByEmail(signupDto.getEmail()).orElse(null);

        if(existingUser != null){
            throw new RuntimeConflictException("User already exists cannot signup with email" + signupDto.getEmail());
        }

        //All the things that needs to created for user will be done here
        User user = modelMapper.map(signupDto, User.class);
        //By default any user signedup as rider only
        user.setRoles(Set.of(Roles.RIDER));
        User savedUser = userRespository.save(user);

        // Created user related entities
        riderService.createNewRider(savedUser);

//        TODO : Add wallet related service here
//        This will create a new wallet whenever user signup
        walletService.createNewWallet(savedUser);

//        converting user entity to user dto
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId,String vehicleId) {
        User user = userRespository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with is id does not exists"+userId));

        if(user.getRoles().contains(DRIVER)){
            throw new RuntimeConflictException("USer is already a driver with this id:"+userId);
        }

//        We need a builder pattern in Driver Entity if we want to build a driver here
        Driver createDriver = Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();

        user.getRoles().add(DRIVER);
        userRespository.save(user);
        Driver savedDriver =  driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver, DriverDto.class);

    }
}
