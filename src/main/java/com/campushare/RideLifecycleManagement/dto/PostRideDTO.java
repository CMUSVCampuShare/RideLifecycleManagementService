package com.campushare.RideLifecycleManagement.dto;

import java.util.UUID;

public class PostRideDTO {
    public UUID rideId;
    public String driverId;
    public int maxSeats;
    public RideStatus rideStatus;

    public enum RideStatus {
        ON_GOING;
    }

    public PostRideDTO(UUID rideId, String driverId, int maxSeats, RideStatus rideStatus) {
        this.rideId = rideId;
        this.driverId = driverId;
        this.maxSeats = maxSeats;
        this.rideStatus = rideStatus;
    }

}
