package com.project.uber.uberApp.dtos;

import com.project.uber.uberApp.entities.enums.PaymentMethod;
import com.project.uber.uberApp.entities.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideDto {

    private Long id;

    private PointDto pickupLocation;

    private PointDto dropoffLocation;

    //when driver accepts your ride
    private LocalDateTime createdTime;

    private String otp;

    //Make sure that you should use correct alias for UserDto that would match UserEntity alias i.e user -> in both dto & entity class
    private RiderDto rider;

    //Make sure that you should use correct alias for UserDto that would match UserEntity alias i.e user -> in both dto & entity class
    private DriverDto driver;

    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private Double fare;

    //when your ride started
    private LocalDateTime startedAt;

    //when your ride ended
    private LocalDateTime endedAt;
}
