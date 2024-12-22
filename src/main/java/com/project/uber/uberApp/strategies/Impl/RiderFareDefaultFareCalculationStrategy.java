package com.project.uber.uberApp.strategies.Impl;

import com.project.uber.uberApp.dtos.RideRequestDto;
import com.project.uber.uberApp.entities.RideRequest;
import com.project.uber.uberApp.services.DistanceService;
import com.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiderFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),rideRequest.getDropoffLocation());
        return distance*RIDE_FARE_MULTIPLIER;
    }
}
