package com.campushare.RideLifecycleManagement.controller;
import com.campushare.RideLifecycleManagement.exceptions.NoSeatLeftException;
import com.campushare.RideLifecycleManagement.exceptions.RideNotFoundException;
import com.campushare.RideLifecycleManagement.service.RideService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/rides")
public class RideController {
    private static final Logger logger = LoggerFactory.getLogger(RideService.class);

    @Autowired
    private RideService rideService;

    @PostMapping("/approveJoinRequest")
    public String approveJoinRequest(@RequestParam String rideId, @RequestParam String passengerId) throws RideNotFoundException, NoSeatLeftException {
        rideService.approveJoinRequest(rideId, passengerId);
        return "Join request for " + passengerId + " to join " + rideId + " is approved, available seats reduced by 1";
    }

    @PostMapping("/rejectJoinRequest")
    public String rejectJoinRequest(@RequestParam String rideId, @RequestParam String rideTitle,  @RequestParam String passengerId) throws RideNotFoundException, JsonProcessingException {
        rideService.rejectJoinRequest(rideId, rideTitle, passengerId);
        return "Join request for " + passengerId + " to join " + rideId + " (rideTitle: " + rideTitle + ") is rejected";
    }

    @PostMapping("/completeRide")
    public String completeRide(@RequestParam String rideId, @RequestParam String driverId, @RequestParam String[] passengerIds) throws RideNotFoundException, JsonProcessingException {
        rideService.completeRide(rideId, driverId, passengerIds);
        return "Ride " + rideId + " by " + driverId + " is completed.";
    }
}

/*
 TODO: Frontend should be like:

 fetch('/api/rides/rejectJoinRequest', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: new URLSearchParams({
        'rideId': 'ride的ID',
        'passengerId': 'passenger的ID'
    })
})
.then(response => response.text())
.then(data => console.log(data));
 */
