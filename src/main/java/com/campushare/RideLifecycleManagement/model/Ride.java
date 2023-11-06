package com.campushare.RideLifecycleManagement.model;

import com.campushare.RideLifecycleManagement.dto.PostRideDTO.Post;
import com.campushare.RideLifecycleManagement.dto.PostRideDTO.Type;
import com.campushare.RideLifecycleManagement.dto.PostRideDTO.Status;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collation = "RideManager")
@Data
public class Ride {

    @Id
    private String rideId;

    private String driverId;
    private Type type;
    private Integer noOfSeats;
    private Status status;
    private Date timestamp;

    private List<String> passengerIds = new ArrayList<>();

    public Ride(Post post) {
        this.rideId = post.getPostId();
        this.driverId = post.getUserId();
        this.type = post.getType();
        this.noOfSeats = post.getNoOfSeats();
        this.status = post.getStatus();
    }

    public void addPassenger(String passengerId) {
        this.passengerIds.add(passengerId);
    }

    public void removePassenger(String passengerId) {
        this.passengerIds.remove(passengerId);
    }
}