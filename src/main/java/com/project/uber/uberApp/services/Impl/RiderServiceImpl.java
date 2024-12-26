package com.project.uber.uberApp.services.Impl;

import com.project.uber.uberApp.dtos.DriverDto;
import com.project.uber.uberApp.dtos.RideDto;
import com.project.uber.uberApp.dtos.RideRequestDto;
import com.project.uber.uberApp.dtos.RiderDto;
import com.project.uber.uberApp.entities.*;
import com.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.project.uber.uberApp.entities.enums.RideStatus;
import com.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.project.uber.uberApp.repositories.RideRequestRepository;
import com.project.uber.uberApp.repositories.RiderRepository;
import com.project.uber.uberApp.services.DriverService;
import com.project.uber.uberApp.services.RatingService;
import com.project.uber.uberApp.services.RideService;
import com.project.uber.uberApp.services.RiderService;
import com.project.uber.uberApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final DriverService driverService;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideStrategyManager rideStrategyManager;
    private final RatingService ratingService;

    @Override
//    This will make sure that either all the transaction should happen or nothing would happen
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {

        Rider rider = getCurrentRider();

        //       Converting dto  to entity
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);

        //Setting ride request status & rider in entity
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        //Calculate the fare from the method of service
        //Strategy to calculate fare
        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);

        //Setting calculated fare in the entity
        rideRequest.setFare(fare);

//        Saving the new ride request in repo
//        We will first make the drive request then search for driver
        RideRequest rideRequest1 = rideRequestRepository.save(rideRequest);

//        Search for driver
        List<Driver> drivers =  rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);
//    TODO : Send notification to all the drivers about this ride request

        return modelMapper.map(rideRequest1, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {

        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider is not allowed to cancel the ride");
        }

        //  if ride is confirmed you cannot cancel it
        if(ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled,Invalid ride"+ride.getRideStatus());
        }

        Ride updatedRide =  rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(),true);


        return modelMapper.map(updatedRide, RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if(!rider.equals(ride.getRider())) {
            throw new RuntimeException("Rider is not the owner of this Ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride status is not Ended hence cannot start rating, status: "+ride.getRideStatus());
        }

        return ratingService.rateDriver(ride, rating);
    }

    @Override
    public RiderDto getMyProfile() {
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider rider = getCurrentRider();
//     using map we can convert one page to another page
        return rideService.getAllRidesOfRider(rider,pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Rider createNewRider(User user) {
     //In order to use builder pattern here we need to
        // use @Builder annotation in Rider Entity to create rider
       Rider rider = Rider
               .builder()
               .user(user)
               .rating(0.0)
               .build();

       return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
//        TODO : Implement Spring Security
        return riderRepository.findById(1L).orElseThrow(()-> new ResourceNotFoundException("Rider not found"));
    }
}
