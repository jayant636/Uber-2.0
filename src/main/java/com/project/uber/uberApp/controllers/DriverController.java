package com.project.uber.uberApp.controllers;

import com.project.uber.uberApp.dtos.*;
import com.project.uber.uberApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/drivers")
@Secured("ROLE_DRIVER")
public class DriverController {

    private final DriverService driverService;

    @PostMapping(path = "/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping(path = "/startRide/{rideRequestId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideRequestId, @RequestBody RideStartDto rideStartDto){
//        to get the otp from the driver we have created a separate dto
        return ResponseEntity.ok(driverService.startRide(rideRequestId,rideStartDto.getOtp()));
    }

    @PostMapping(path = "/endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId){
//        to get the otp from the driver we have created a separate dto
        return ResponseEntity.ok(driverService.endRide(rideId));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto>  cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.cancelRide(rideId)) ;
    }

    @PostMapping(path = "/rateRider")
    public ResponseEntity<RiderDto> rateRider(@RequestBody RatingDto rateDto){
        return ResponseEntity.ok(driverService.rateRider(rateDto.getRideId(), rateDto.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDto> getMyProfile(){
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @GetMapping(path = "/getMyRides")
    public ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0")Integer pageOffset, @RequestParam(defaultValue = "10",required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffset,pageSize, Sort.by(Sort.Direction.DESC,"createdTime","id"));
        return ResponseEntity.ok(driverService.getAllMyRides(pageRequest));
    }

}
