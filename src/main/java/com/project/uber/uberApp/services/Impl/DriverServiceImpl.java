package com.project.uber.uberApp.services.Impl;

import com.project.uber.uberApp.dtos.DriverDto;
import com.project.uber.uberApp.dtos.RideDto;
import com.project.uber.uberApp.dtos.RiderDto;
import com.project.uber.uberApp.entities.RideRequest;
import com.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.project.uber.uberApp.repositories.DriverRepository;
import com.project.uber.uberApp.services.DriverService;
import com.project.uber.uberApp.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;

    @Override
    @Transactional
    public RideDto cancelRide(Long rideRequestId) {
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);

        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride request cannot be accepted :");
        }
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);

        return null;
    }

    @Override
    public RideDto startRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto endRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto acceptRide(Long rideId) {
        return null;
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }
}
