package com.campushare.RideLifecycleManagement.state;

import com.campushare.RideLifecycleManagement.dto.PostRideDTO;
import com.campushare.RideLifecycleManagement.exceptions.NoSeatLeftException;
import com.campushare.RideLifecycleManagement.exceptions.RideAlreadyStartedException;
import com.campushare.RideLifecycleManagement.model.Ride;
import com.campushare.RideLifecycleManagement.service.RideService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

public class FullState implements RideState {
    @Autowired
    RideService rideService;
    @Override
    public void approveJoinRequest(Ride ride, String passengerId) {
        throw new NoSeatLeftException("Sorry, no seats left for this ride: " + ride.getRideId());
    }

    @Override
    public void rejectJoinRequest(Ride ride, String passengerId) {
        try {
            rideService.rejectJoinRequest(ride.getRideId(), ride.getTitle(), passengerId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startRide(Ride ride) {
        throw new RideAlreadyStartedException("Action cannot complete: The ride " + ride.getRideId() + " was already started.");
    }

    @Override
    public void completeRide(Ride ride) {
        try {
            ride.setStatus(PostRideDTO.Status.COMPLETED);
            ride.changeState(new CompletedState());
            rideService.completeRide(ride);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cancelRide(Ride ride) {
        ride.setStatus(PostRideDTO.Status.CANCELED);
        ride.changeState(new CanceledState());
        rideService.updateRideState(ride);
    }
}
