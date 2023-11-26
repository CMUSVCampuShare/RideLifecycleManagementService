package com.campushare.RideLifecycleManagement.model;

import com.campushare.RideLifecycleManagement.dto.PostRideDTO.Post;
import com.campushare.RideLifecycleManagement.dto.PostRideDTO.Type;
import com.campushare.RideLifecycleManagement.dto.PostRideDTO.Status;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "ride")
@Data
@NoArgsConstructor
public class Ride {

    @Id
    private String rideId;

    private String driverId;
    private String title;
    private String from;
    private String to;
    private Type type;
    private Integer noOfSeats;
    private Status status;
    private Date timestamp;

    private List<String> passengerIds = new ArrayList<>();

    public Ride(Post post) {
        this.rideId = post.getPostId();
        this.driverId = post.getUserId();
        this.title = post.getTitle();
        this.from = post.getFrom();
        this.to = post.getTo();
        this.type = post.getType();
        this.noOfSeats = post.getNoOfSeats();
        this.status = post.getStatus();
        this.timestamp = post.getTimestamp();
    }

    public void addPassenger(String passengerId) {
        if (this.noOfSeats > 0) {
            this.noOfSeats--;
            this.passengerIds.add(passengerId);
        }
    }

    public void removePassenger(String passengerId) {
        this.noOfSeats++;
        this.passengerIds.remove(passengerId);
    }
}