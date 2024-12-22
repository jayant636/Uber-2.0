package com.project.uber.uberApp.entities;

import com.project.uber.uberApp.entities.enums.PaymentMethod;
import com.project.uber.uberApp.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Table(name = "ride_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point , 4326)")
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(Point , 4326)")
    private Point dropoffLocation;

    @CreationTimestamp
    private LocalDateTime requestedTime;

    //Fetchtype will make sure that when riderequest will be called it will
    // share the data of ride request only it will not fetch the rider
    //ManytoOne coz -> One rider can have many rides
    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideRequestStatus rideRequestStatus;

    private Double fare;
}
