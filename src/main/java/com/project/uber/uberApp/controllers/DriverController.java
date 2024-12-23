package com.project.uber.uberApp.controllers;

import com.project.uber.uberApp.dtos.RideDto;
import com.project.uber.uberApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/drivers")
public class DriverController {

    private final DriverService driverService;

    @PostMapping(path = "/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> rideDto(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }
}
