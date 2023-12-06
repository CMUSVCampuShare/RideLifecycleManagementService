package com.campushare.RideLifecycleManagement.state;

import com.campushare.RideLifecycleManagement.exceptions.NoSeatLeftException;
import com.campushare.RideLifecycleManagement.model.Ride;

public interface RideState {
    void approveJoinRequest(Ride ride, String passengerId);
    void rejectJoinRequest(Ride ride, String passengerId);
    void startRide(Ride ride);
    void completeRide(Ride ride);
    void cancelRide(Ride ride);
}
