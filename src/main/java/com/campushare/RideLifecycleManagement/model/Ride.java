package com.campushare.RideLifecycleManagement.model;

import com.campushare.RideLifecycleManagement.dto.PostRideDTO.Post;
import com.campushare.RideLifecycleManagement.dto.PostRideDTO.Type;
import com.campushare.RideLifecycleManagement.dto.PostRideDTO.Status;

import com.campushare.RideLifecycleManagement.exceptions.NoSeatLeftException;
import com.campushare.RideLifecycleManagement.state.CreatedState;
import com.campushare.RideLifecycleManagement.state.RideState;
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

    private RideState currentState;


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
        this.currentState = new CreatedState();
    }

    public void changeState(RideState newState) {
        this.currentState = newState;
    }

    public void startRide(String passengerId) {
        this.currentState.startRide(this);
    }

    public void approveJoinRequest(String passengerId) throws NoSeatLeftException {
        this.currentState.approveJoinRequest(this, passengerId);
    }

    public void rejectJoinRequest(String passengerId) {
        this.currentState.rejectJoinRequest(this, passengerId);
    }


    public void completeRide() {
        this.currentState.completeRide(this);
    }

    public void cancelRide() {
        this.currentState.cancelRide(this);
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