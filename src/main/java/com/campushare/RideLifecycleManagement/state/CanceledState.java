package com.campushare.RideLifecycleManagement.state;

import com.campushare.RideLifecycleManagement.exceptions.RideAlreadyCanceledException;
import com.campushare.RideLifecycleManagement.model.Ride;

public class CanceledState implements RideState {
    @Override
    public void approveJoinRequest(Ride ride, String passengerId) {
        throw new RideAlreadyCanceledException("Action cannot complete: The ride " + ride.getRideId() + " was already canceled.");
    }

    @Override
    public void rejectJoinRequest(Ride ride, String passengerId) {
        throw new RideAlreadyCanceledException("Action cannot complete: The ride " + ride.getRideId() + " was already canceled.");
    }

    @Override
    public void startRide(Ride ride) {
        throw new RideAlreadyCanceledException("Action cannot complete: The ride " + ride.getRideId() + " was already canceled.");
    }

    @Override
    public void completeRide(Ride ride) {
        throw new RideAlreadyCanceledException("Action cannot complete: The ride " + ride.getRideId() + " was already canceled.");
    }

    @Override
    public void cancelRide(Ride ride) {
        throw new RideAlreadyCanceledException("Action cannot complete: The ride " + ride.getRideId() + " was already canceled.");
    }
}
