package com.project.uber.uberApp.services;

import com.project.uber.uberApp.dtos.DriverDto;
import com.project.uber.uberApp.dtos.RideDto;
import com.project.uber.uberApp.dtos.RiderDto;

import java.util.List;

public interface DriverService {

    RideDto cancelRide(Long rideRequestId);

    RideDto startRide(Long rideId);

    RideDto endRide(Long rideId);

    RideDto acceptRide(Long rideId);

    RiderDto rateRider(Long rideId,Integer rating);

    DriverDto getMyProfile();

    List<RideDto> getAllMyRides();

}
