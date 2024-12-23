package com.project.uber.uberApp.strategies;

import com.project.uber.uberApp.strategies.Impl.DriverMatchingHighestRated;
import com.project.uber.uberApp.strategies.Impl.DriverMatchingNearestDriverStrategy;
import com.project.uber.uberApp.strategies.Impl.RideFareDefaultFareCalculationStrategy;
import com.project.uber.uberApp.strategies.Impl.RideFareSurgePricingFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRated driverMatchingHighestRated;
    private final DriverMatchingNearestDriverStrategy driverMatchingNearestDriverStrategy;
    private final RideFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating){
      if(riderRating >= 4.5){
        return driverMatchingHighestRated;
      }else{
          return driverMatchingNearestDriverStrategy;
      }
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){
//       6PM TO 9 PM
        LocalTime surgeStartTime = LocalTime.of(18,0);
        LocalTime surgeEndTime = LocalTime.of(21,0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime){
            return surgePricingFareCalculationStrategy;
        }else {
            return defaultFareCalculationStrategy;
        }

    }

}
