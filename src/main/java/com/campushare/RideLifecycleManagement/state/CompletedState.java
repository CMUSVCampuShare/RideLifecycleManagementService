package com.campushare.RideLifecycleManagement.state;

import com.campushare.RideLifecycleManagement.exceptions.RideAlreadyCompletedException;
import com.campushare.RideLifecycleManagement.model.Ride;

public class CompletedState implements RideState {
    @Override
    public void approveJoinRequest(Ride ride, String passengerId) {
        throw new RideAlreadyCompletedException("Action cannot complete: The ride " + ride.getRideId() + " was already completed.");
    }

    @Override
    public void rejectJoinRequest(Ride ride, String passengerId) {
        throw new RideAlreadyCompletedException("Action cannot complete: The ride " + ride.getRideId() + " was already completed.");
    }

    @Override
    public void startRide(Ride ride) {
        throw new RideAlreadyCompletedException("Action cannot complete: The ride " + ride.getRideId() + " was already completed.");
    }

    @Override
    public void completeRide(Ride ride) {
        throw new RideAlreadyCompletedException("Action cannot complete: The ride " + ride.getRideId() + " was already completed.");
    }

    @Override
    public void cancelRide(Ride ride) {
        throw new RideAlreadyCompletedException("Action cannot complete: The ride " + ride.getRideId() + " was already completed.");
    }
}
