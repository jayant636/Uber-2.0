package com.project.uber.uberApp.services;

import com.project.uber.uberApp.dtos.DriverDto;
import com.project.uber.uberApp.dtos.RideDto;
import com.project.uber.uberApp.dtos.RideRequestDto;
import com.project.uber.uberApp.dtos.RiderDto;
import com.project.uber.uberApp.entities.Rider;
import com.project.uber.uberApp.entities.User;

import java.util.List;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    List<RideDto> getAllMyRides();

    Rider createNewRider(User user);

    Rider getCurrentRider();
}
