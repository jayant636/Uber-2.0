package com.project.uber.uberApp.services.Impl;

import com.project.uber.uberApp.dtos.DriverDto;
import com.project.uber.uberApp.dtos.RideDto;
import com.project.uber.uberApp.dtos.RiderDto;
import com.project.uber.uberApp.entities.Driver;
import com.project.uber.uberApp.entities.Ride;
import com.project.uber.uberApp.entities.RideRequest;
import com.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.project.uber.uberApp.entities.enums.RideStatus;
import com.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.project.uber.uberApp.repositories.DriverRepository;
import com.project.uber.uberApp.services.DriverService;
import com.project.uber.uberApp.services.RideRequestService;
import com.project.uber.uberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final ModelMapper modelMapper;
    private final RideService rideService;
    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);

        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride request cannot be accepted :");
        }

        Driver currentDriver = getCurrentDriver();

        if(!currentDriver.getAvailable()){
            throw new RuntimeException("Current Driver Is not available ");
        }

        // Since driver is occupied so mark it as not available
        Driver savedDriver =  updateDriverAvailability(currentDriver,false);

        Ride ride = rideService.createNewRide(rideRequest,savedDriver);

        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto startRide(Long rideId,String otp) {
//       fetch the ride from db
       Ride ride = rideService.getRideById(rideId);

//       fetch currentDriver
       Driver driver = getCurrentDriver();
//       if current driver is same aas ride driver then we should continue
       if(!driver.equals(ride.getDriver())){
           throw new RuntimeException("Driver cannot start ride as he has not accepted it earlier");
       }

//       Check ride status
       if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
           throw new RuntimeException("Ride status is not Confirmed hance cannot be started , status:"+ride.getRideStatus());
       }

//       Match the otp
       if(!ride.getOtp().equals(otp)){
           throw new RuntimeException("OTP is not valid");
       }

       ride.setStartedAt(LocalDateTime.now());
       Ride savedRide =  rideService.updateRideStatus(ride,RideStatus.ONGOING);
       return modelMapper.map(savedRide, RideDto.class);

    }

    @Override
    public RideDto endRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
       Ride ride = rideService.getRideById(rideId);

        // fetch currentDriver
        Driver driver = getCurrentDriver();
       // if current driver is same aas ride driver then we should continue
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start ride as he has not accepted it earlier");
        }

//        if ride is confirmed you cannot cancel it
        if(ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled,Invalid ride"+ride.getRideStatus());
        }

//        update ride status to cancelled
        rideService.updateRideStatus(ride,RideStatus.CANCELLED);
//       set the driver free
        updateDriverAvailability(driver,true);

        return modelMapper.map(ride, RideDto.class);

    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        Driver driver = getCurrentDriver();
        return modelMapper.map(driver, DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
       Driver currentDriver = getCurrentDriver();
//     using map we can convert one page to another page
       return rideService.getAllRidesOfDriver(currentDriver.getId(),pageRequest).map(
               ride -> modelMapper.map(ride, RideDto.class)
       );
    }

    @Override
    public Driver getCurrentDriver() {
        return driverRepository.findById(2L).orElseThrow(()->new ResourceNotFoundException("Current Driver does not found"));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, Boolean available) {

        driver.setAvailable(available);
        return   driverRepository.save(driver);
    }
}
