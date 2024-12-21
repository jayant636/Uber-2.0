package com.project.uber.uberApp.dtos;

import com.project.uber.uberApp.entities.enums.PaymentMethod;
import com.project.uber.uberApp.entities.enums.RideRequestStatus;
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
public class RideRequestDto {


    private Long id;

    private Point pickupLocation;

    private Point dropoffLocation;

    private LocalDateTime requestedTime;

    private RiderDto riderDto;

    private PaymentMethod paymentMethod;

    private RideRequestStatus rideRequestStatus;
}
