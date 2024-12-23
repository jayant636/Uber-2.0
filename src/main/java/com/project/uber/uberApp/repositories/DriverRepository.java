package com.project.uber.uberApp.repositories;

import com.project.uber.uberApp.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//Custom method
//ST_Distance(point1 , point2) - It will calculate the distance between two points
//ST_DWithin(point1, 10000) - It will calculate the radius from that point

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
//    Space after AS distance is important or else query will break
//     Select driver & distance from table driver where driver is available & driver is within 10km radius
//     of pickup point . Order will be ascending & 10 drivers will shown at max .
    @Query(value = "SELECT d.*, ST_Distance(d.current_location, :pickupLocation) AS distance " +
            "FROM driver d " +
            "where d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 10000) " +
            "ORDER BY distance " +
            "LIMIT 10",nativeQuery = true
    )
    List<Driver> findTenNearestDrivers(Point pickupLocation);


    @Query(value = "SELECT d.* " +
            "FROM driver d " +
            "WHERE d.available= true AND ST_DWithin(d.current_location, :pickupLocation , 15000) " +
            "ORDER BY d.rating DESC " +
            "LIMIT 10 ",nativeQuery = true)
    List<Driver> findTenNearByTopRatedDrivers(Point pickupLocation);


}
