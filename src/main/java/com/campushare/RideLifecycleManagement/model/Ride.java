package com.campushare.RideLifecycleManagement.model;

import com.campushare.RideLifecycleManagement.dto.PostRideDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collation = "RideManager")
public class Ride {

    @Id
    private String rideId;

    private String driverId;
    private int maxSeats;
    private PostRideDTO.RideStatus rideStatus;

    public Ride(PostRideDTO postRideDTO) {
        this.rideId = postRideDTO.rideId.toString();
        this.driverId = postRideDTO.driverId;
        this.maxSeats = postRideDTO.maxSeats;
        this.rideStatus = postRideDTO.rideStatus;
    }
}