package com.project.uber.uberApp.strategies;

import com.project.uber.uberApp.dtos.RideRequestDto;
import com.project.uber.uberApp.entities.Driver;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDriver(RideRequestDto rideRequestDto);
}
