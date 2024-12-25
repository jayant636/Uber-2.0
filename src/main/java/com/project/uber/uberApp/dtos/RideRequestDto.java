package com.project.uber.uberApp.dtos;

import com.project.uber.uberApp.entities.enums.PaymentMethod;
import com.project.uber.uberApp.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {


    private Long id;

    private PointDto pickupLocation;

    private PointDto dropoffLocation;

    private LocalDateTime requestedTime;

    //    Make sure that you should use correct alias for UserDto that would match UserEntity alias i.e user -> in both dto & entity class
    private RiderDto rider;

    private Double fare;

    private PaymentMethod paymentMethod;

    private RideRequestStatus rideRequestStatus;
}
