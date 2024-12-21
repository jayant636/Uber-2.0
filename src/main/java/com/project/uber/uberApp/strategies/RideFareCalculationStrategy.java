package com.project.uber.uberApp.strategies;

import com.project.uber.uberApp.dtos.RideRequestDto;

public interface RideFareCalculationStrategy {

    double calculateFare(RideRequestDto rideRequestDto);


}
