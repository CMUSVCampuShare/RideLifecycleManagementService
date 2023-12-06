package com.campushare.RideLifecycleManagement.state;

import com.campushare.RideLifecycleManagement.dto.PostRideDTO;
import com.campushare.RideLifecycleManagement.exceptions.NoSeatLeftException;
import com.campushare.RideLifecycleManagement.model.Ride;
import com.campushare.RideLifecycleManagement.service.RideService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

public class CreatedState implements RideState {
    @Autowired
    RideService rideService;

    @Override
    public void approveJoinRequest(Ride ride, String passengerId) {
        if (ride.getNoOfSeats() == 0) {
            ride.setStatus(PostRideDTO.Status.FULL);
            throw new NoSeatLeftException("Sorry, no seats left for this ride: " + ride.getRideId());
        } else if (ride.getNoOfSeats() == 1) {
            rideService.approveJoinRequest(ride, passengerId);
            ride.setStatus(PostRideDTO.Status.FULL);
            ride.changeState(new FullState());
        } else {
            rideService.approveJoinRequest(ride, passengerId);
            ride.setStatus(PostRideDTO.Status.ONGOING);
            ride.changeState(new OngoingState());
        }
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
        if (ride.getNoOfSeats() > 0) {
            ride.setStatus(PostRideDTO.Status.ONGOING);
            ride.changeState(new OngoingState());
        } else {
            ride.setStatus(PostRideDTO.Status.FULL);
            ride.changeState(new FullState());
        }
        rideService.updateRideState(ride);
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
