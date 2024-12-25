package com.project.uber.uberApp.controllers;

import com.project.uber.uberApp.dtos.*;
import com.project.uber.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rider")
@RequiredArgsConstructor
public class RiderController {

    //We're using this service bcoz ride will be requested by rider first only
    private final RiderService riderService;


    @PostMapping("/requestRide")
    public ResponseEntity<RideRequestDto>  requestRide(@RequestBody RideRequestDto rideRequestDto){
      return ResponseEntity.ok(riderService.requestRide(rideRequestDto)) ;
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto>  cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(riderService.cancelRide(rideId)) ;
    }

    @PostMapping(path = "/rateDriver/{rideId}")
    public ResponseEntity<DriverDto> rateDriver(@RequestBody RatingDto rateDto){
        return ResponseEntity.ok(riderService.rateDriver(rateDto.getRideId(), rateDto.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<RiderDto> getMyProfile(){
        return ResponseEntity.ok(riderService.getMyProfile());
    }

    @GetMapping(path = "/getMyRides")
    public ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0")Integer pageOffset,@RequestParam(defaultValue = "10",required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffset,pageSize, Sort.by(Sort.Direction.DESC,"createdTime","id"));
        return ResponseEntity.ok(riderService.getAllMyRides(pageRequest));
    }

}
