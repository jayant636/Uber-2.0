package com.project.uber.uberApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {

    private Long id;
//    Make sure that you should use correct alias for UserDto that would match UserEntity alias i.e user -> in both dto & entity class
    private UserDto user;
    private Double rating;

    private Boolean available;
    private String vehicleId;
}
