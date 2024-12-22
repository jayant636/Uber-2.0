package com.project.uber.uberApp.services.Impl;

import com.project.uber.uberApp.dtos.DriverDto;
import com.project.uber.uberApp.dtos.RideDto;
import com.project.uber.uberApp.dtos.RideRequestDto;
import com.project.uber.uberApp.dtos.RiderDto;
import com.project.uber.uberApp.entities.RideRequest;
import com.project.uber.uberApp.entities.Rider;
import com.project.uber.uberApp.entities.User;
import com.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.project.uber.uberApp.repositories.RideRequestRepository;
import com.project.uber.uberApp.repositories.RiderRepository;
import com.project.uber.uberApp.services.RiderService;
import com.project.uber.uberApp.strategies.DriverMatchingStrategy;
import com.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final  DriverMatchingStrategy driverMatchingStrategy;
    private final RideFareCalculationStrategy rideFareCalculationStrategy;
    private final ModelMapper modelMapper;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
//       Converting dto  to entity
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);

        //Setting ride request status in entity
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        //Calculate the fare from the method of service
        //Strategy to calculate fare
        Double fare = rideFareCalculationStrategy.calculateFare(rideRequest);

        //Setting calculated fare in the entity
        rideRequest.setFare(fare);

//        Saving the new ride request in repo
//        We will first make the drive request then search for driver
        RideRequest rideRequest1 = rideRequestRepository.save(rideRequest);

//        Search for driver
        driverMatchingStrategy.findMatchingDriver(rideRequest);

        return modelMapper.map(rideRequest1, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
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
}
