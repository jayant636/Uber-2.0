package com.project.uber.uberApp.services.Impl;

import com.project.uber.uberApp.dtos.DriverDto;
import com.project.uber.uberApp.dtos.RiderDto;
import com.project.uber.uberApp.entities.Driver;
import com.project.uber.uberApp.entities.Rating;
import com.project.uber.uberApp.entities.Ride;
import com.project.uber.uberApp.entities.Rider;
import com.project.uber.uberApp.repositories.DriverRepository;
import com.project.uber.uberApp.repositories.RatingRepository;
import com.project.uber.uberApp.repositories.RiderRepository;
import com.project.uber.uberApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final ModelMapper modelMapper;
    private final RiderRepository riderRepository;
    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;

    @Override
    public DriverDto rateDriver(Ride ride, Integer rating) {
//        Fetch the rating linked to ride
        Rating rating1 = ratingRepository.findByRide(ride).orElseThrow(()-> new RuntimeException("Ride does not exist"));
        Driver driver = ride.getDriver();
//
        if(rating1.getRiderRating() != null){
            throw new RuntimeException("Driver has already been rated,Cannot rate again ");
        }
//
//        Set the driver rating
        rating1.setDriverRating(rating);

//        save the rating in Rating Table
        ratingRepository.save(rating1);

//        Calculate the avg of all the rating
        Double newDriverRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(rating2 -> rating2.getDriverRating())
                .average().orElse(0.0);

//        Set the rating of driver in driver table
        driver.setRating(newDriverRating);
        Driver driver1 = driverRepository.save(driver);
        return modelMapper.map(driver1, DriverDto.class);
    }

    @Override
    public RiderDto rateRider(Ride ride, Integer rating) {

        //        Fetch the rating linked to ride
        Rating rating1 = ratingRepository.findByRide(ride).orElseThrow(()-> new RuntimeException("Ride does not exist"));
        Rider rider = ride.getRider();

        if(rating1.getRiderRating() != null){
            throw new RuntimeException("rider has already been rated,Cannot rate again ");
        }

        //        Set the driver rating
        rating1.setDriverRating(rating);

//        save the rating in Rating Table
        ratingRepository.save(rating1);

//        Calculate the avg of all the rating
        Double newRiderRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(rating2 -> rating2.getRiderRating())
                .average().orElse(0.0);

//        Set the rating of driver in driver table
        rider.setRating(newRiderRating);
        Rider rider1 = riderRepository.save(rider);
        return modelMapper.map(rider1,RiderDto.class);
    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();
        ratingRepository.save(rating);
    }
}
